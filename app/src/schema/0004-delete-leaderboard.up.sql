-- drop leaderboard table since the calculation is happening on the fly
DROP INDEX IF EXISTS idx_leaderboard_user_id;
DROP TRIGGER IF EXISTS update_leaderboard_updated_at ON leaderboard;
DROP TABLE IF EXISTS leaderboard;
