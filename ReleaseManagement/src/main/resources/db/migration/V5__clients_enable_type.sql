CREATE TABLE Clients (
    id UUID PRIMARY KEY,
    account_id VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Feature_clients (
    client_id UUID REFERENCES Clients(id) ON DELETE CASCADE,
    feature_id UUID REFERENCES Features(id) ON DELETE CASCADE,
    PRIMARY KEY (client_id, feature_id)
);

CREATE TYPE enable_type AS ENUM ('ALL', 'PER_ACCOUNT');

ALTER TABLE features
ADD COLUMN enable_type enable_type DEFAULT 'ALL' NOT NULL;