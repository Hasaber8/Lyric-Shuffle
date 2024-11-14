-- Drop triggers first
DROP TRIGGER IF EXISTS update_leaderboard_updated_at ON leaderboard;
DROP TRIGGER IF EXISTS update_users_updated_at ON users;

-- Drop tables in reverse order (to handle foreign key dependencies)
DROP TABLE IF EXISTS leaderboard;
DROP TABLE IF EXISTS play_session;
DROP TABLE IF EXISTS lyrics;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS users;

-- Drop trigger function
DROP FUNCTION IF EXISTS update_updated_at_column();

-- Drop custom types
DROP TYPE IF EXISTS difficulty_level;

-- Drop extensions
DROP EXTENSION IF EXISTS "uuid-ossp";
