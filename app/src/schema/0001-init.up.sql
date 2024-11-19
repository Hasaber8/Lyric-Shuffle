-- Create custom type for difficulty levels
CREATE TYPE difficulty_level AS ENUM ('easy', 'medium', 'hard');

-- Create extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create trigger to update the updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_users_username ON users (username);

CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Songs table
CREATE TABLE IF NOT EXISTS songs (
    song_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    difficulty_level difficulty_level NOT NULL,
    CONSTRAINT valid_title CHECK (LENGTH(TRIM(title)) > 0),
    CONSTRAINT valid_artist CHECK (LENGTH(TRIM(artist)) > 0)
);

CREATE INDEX IF NOT EXISTS idx_songs_genre ON songs (genre);
CREATE INDEX IF NOT EXISTS idx_songs_difficulty_level ON songs (difficulty_level);

-- Lyrics table
CREATE TABLE IF NOT EXISTS lyrics (
    lyric_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    lyric_order_id INTEGER,
    song_id UUID NOT NULL REFERENCES songs(song_id) ON DELETE CASCADE,
    lyrics TEXT NOT NULL CHECK (LENGTH(TRIM(lyrics)) > 0),
    CONSTRAINT unique_song_lyric_order UNIQUE (song_id, lyric_order_id)
);

CREATE INDEX IF NOT EXISTS idx_lyrics_song_id ON lyrics (song_id);
CREATE INDEX IF NOT EXISTS idx_lyrics_song_order ON lyrics (song_id, lyric_order_id);

-- Play session table
CREATE TABLE IF NOT EXISTS play_session (
    play_session_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    song_id UUID NOT NULL REFERENCES songs(song_id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    score DECIMAL(10,2) NOT NULL CHECK (score >= 0),
    date_played TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_play_session_user_id ON play_session USING btree (user_id);

-- Leaderboard table
CREATE TABLE IF NOT EXISTS leaderboard (
    leaderboard_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    average_score DECIMAL(10,2) NOT NULL CHECK (average_score >= 0),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_leaderboard_user_id ON leaderboard (user_id);

CREATE TRIGGER update_leaderboard_updated_at
    BEFORE UPDATE ON leaderboard
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
