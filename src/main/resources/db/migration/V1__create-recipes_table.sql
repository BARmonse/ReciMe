CREATE TABLE IF NOT EXISTS recipes (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT,
    ingredients TEXT[] NOT NULL,
    instructions TEXT,
    vegetarian BOOLEAN NOT NULL,
    servings INTEGER NOT NULL
);