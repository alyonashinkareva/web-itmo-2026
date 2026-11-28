package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.wp.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    @Modifying
    @Query(value = "INSERT INTO tag (name) VALUES (?1) ON DUPLICATE KEY UPDATE name = name", nativeQuery = true)
    void insertIfMissing(String name);
}
