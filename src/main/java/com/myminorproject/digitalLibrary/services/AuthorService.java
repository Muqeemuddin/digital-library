package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.models.Author;
import com.myminorproject.digitalLibrary.repository.AuthorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorDao authorDao;

    public Author getOrCreate(Author author){

        // get the Author with the same email
        Author existingAuthor = authorDao.findByEmail(author.getEmail());

        // If not exists, save it to the db and return the same back.
        if(existingAuthor == null){
            existingAuthor = authorDao.save(author);
        }

        return existingAuthor;
    }
}
