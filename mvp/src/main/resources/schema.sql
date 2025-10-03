DROP TABLE IF EXISTS playlist_item CASCADE;
DROP TABLE IF EXISTS playlist CASCADE;
DROP TABLE IF EXISTS song CASCADE;
DROP TABLE IF EXISTS users CASCADE;

--Table utilisateurs
CREATE TABLE users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username TEXT,
    password TEXT
);

-- Table chansons
CREATE TABLE song(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title TEXT

);


-- Table playlists
CREATE TABLE playlist(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title TEXT
);

-- Table relationnelle playlist ↔ song (items ordonnés)

CREATE TABLE playlist_item (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    playlist_id BIGINT NOT NULL REFERENCES playlist(id) ON DELETE CASCADE,
    song_id BIGINT NOT NULL REFERENCES song(id) ON DELETE CASCADE,
    position INT,

    CONSTRAINT uq_playlist_position UNIQUE (playlist_id, position)
    -- CONSTRAINT uq_playlist_song UNIQUE (playlist_id, song_id) à décommentter si je veux que les sons soit uniques dans chaque playlist

);

-- Index pour rechercher vite les items par playlist et ordre
CREATE INDEX idx_playlist_item_playlist_pos ON playlist_item (playlist_id, position);


