SET @post_id = (SELECT id FROM post ORDER BY id DESC LIMIT 1);

INSERT INTO tag (name)
VALUES ('java'), ('spring')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO post_tag (post_id, tag_id)
SELECT @post_id, tag.id
FROM tag
WHERE @post_id IS NOT NULL
  AND tag.name IN ('java', 'spring')
  AND NOT EXISTS (
      SELECT 1 FROM post_tag
      WHERE post_id = @post_id AND tag_id = tag.id
  );
