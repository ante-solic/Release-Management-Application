CREATE TABLE Projects(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Releases(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    create_date DATE NOT NULL,
    release_date DATE NOT NULL,
    project_id UUID REFERENCES Projects(id) ON DELETE CASCADE
);

CREATE TABLE Features(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    release_id UUID REFERENCES Releases(id) ON DELETE CASCADE
);

CREATE TABLE User_roles(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE Users(
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE User_project_roles(
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES Users(id) ON DELETE CASCADE,
    project_id UUID REFERENCES Projects(id) ON DELETE CASCADE,
    role_id UUID REFERENCES User_roles(id) ON DELETE CASCADE
);

