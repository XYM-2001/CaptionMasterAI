from fastapi import FastAPI, UploadFile, File, Form
from pydantic import BaseModel
from typing import Optional, List
from apifunc import connect_db, create_tables
import apifunc

app = FastAPI()

conn = connect_db()
if conn is None:
    exit(1)
create_tables(conn) 

class User(BaseModel):
    username: str
    profile_picture: Optional[str] = None


@app.get("/user")
async def get_user(token: str):
    username, picture = apifunc.get_login(token) 
    user_data = {"username": username, "profile_picture": picture}
    return User(**user_data) 


@app.post("/media/fetch")
async def fetch_media(username: str):
    conn = connect_db()
    cursor = conn.cursor()

    try:
        video_urls = apifunc.get_media(username)
        if not video_urls:
            return {"message": "No media found for user."}
        media_data = [(username, url) for url in video_urls] 
        cursor.executemany("INSERT INTO media (user_id, media_url) VALUES (?, ?)", media_data)
        conn.commit()
        media_ids = [row[0] for row in cursor.execute("SELECT id FROM media WHERE user_id = ?", (username,))]
        return {
            "message": "Media data fetched and stored successfully",
            "media_ids": media_ids if media_ids else [], 
        }
    finally:
        cursor.close() 


@app.get("/media/{media_id}")
async def download_media(media_id: int):
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT url FROM media WHERE id = ?", (media_id,))
        media_url = cursor.fetchone()[0]
        local_path = apifunc.download_and_store_media(media_url)
        cursor.execute("UPDATE media SET local_path = ? WHERE id = ?", (local_path, media_id))
        conn.commit()
        return {"local_path": local_path}
    finally:
        cursor.close() 

class CaptionRequest(BaseModel):
    description: str
    mood: str
    length: int


@app.post("/caption")
async def generate_caption(data: CaptionRequest):
    generated_caption = apifunc.generate_caption(data.description, data.mood, data.length)
    return {"caption": generated_caption}

