package com.mertapp.todolist.repository;

import com.mertapp.todolist.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findNotesByUserid(Long id);

    @Modifying
    @Query("update Note n set n.text = :text where n.id = :id and n.userid = :userid")
    void updateNoteWithId(@Param("text") String text, @Param("id") Long noteid, @Param("userid") Long userid);

    @Modifying
    @Query("delete from Note n where n.id = :id  and n.userid = :userid")
    void deleteNoteWithId(@Param("id") Long id, @Param("userid") Long userid);
}
