INSERT INTO notice (content, creation_time)
SELECT 'Welcome to the website!', NOW()
WHERE NOT EXISTS (SELECT 1 FROM notice WHERE content = 'Welcome to the website!');

INSERT INTO notice (content, creation_time)
SELECT 'Registration is open.', NOW()
WHERE NOT EXISTS (SELECT 1 FROM notice WHERE content = 'Registration is open.');
