package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository < User, Long > {

    @NotNull User findByUsername ( String username );

    User findByEmail ( String email );
    
    Page <User> findAll( Pageable pageable);
}
