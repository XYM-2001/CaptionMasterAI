import sqlite3

def connect_db():
    try:
        conn = sqlite3.connect('your_database.db')
        return conn
    except sqlite3.Error as e:
        print("Error connecting to database:", e)
        return None  

def create_tables(conn):
    cursor = conn.cursor()
    cursor.execute('''CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT UNIQUE,
                        dp_url TEXT
                    )''')
    cursor.execute('''CREATE TABLE IF NOT EXISTS media (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                        media_list TEXT,
                        caption TEXT
                    )''')
    cursor.execute('''CREATE TABLE IF NOT EXISTS captions (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                        description TEXT,
                        mood TEXT,
                        charlen INTEGER
                    )''')
    conn.commit()
    cursor.close()



def get_login(token: str):
    pass


def extract_video_urls(tiktok_api_response: str):
    pass


def insert_media(conn, user_id, media_url):
    cursor = conn.cursor()
    try:
        cursor.execute("INSERT INTO media (user_id, media_url) VALUES (?, ?)", (user_id, media_url))
        conn.commit()
        cursor.execute("SELECT last_insert_rowid()")
        media_id = cursor.fetchone()[0]
        return media_id  
    except sqlite3.Error as e:
        print("Error inserting media data:", e)
        return None  
    finally:
        cursor.close()


def get_media(username):
    pass


def update_media_path(conn, media_id, local_path):
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE media SET local_path = ? WHERE id = ?", (local_path, media_id))
        conn.commit()
    except sqlite3.Error as e:
        print("Error updating media path:", e)
    finally:
        cursor.close()
