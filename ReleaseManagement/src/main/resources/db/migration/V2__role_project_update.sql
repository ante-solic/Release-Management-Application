DROP TABLE IF EXISTS User_project_roles;

DROP TABLE IF EXISTS User_roles;

CREATE TABLE IF NOT EXISTS User_projects (
    user_id UUID REFERENCES Users(id) ON DELETE CASCADE,
    project_id UUID REFERENCES Projects(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, project_id)
);

CREATE TABLE Roles(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS User_roles (
    user_id UUID REFERENCES Users(id) ON DELETE CASCADE,
    role_id UUID REFERENCES Roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);



