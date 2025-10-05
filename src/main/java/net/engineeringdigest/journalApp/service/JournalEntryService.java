package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry JournalEntry, String username){
            try {
                User user  =userService.findByUsername(username);
                JournalEntry.setDate(LocalDateTime.now());
                JournalEntry saved=journalEntryRepository.save(JournalEntry);
                user.getJournalEntries().add(saved);
                userService.saveUser(user);
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("The error occured during throwing the entry " , e);
            }


    }

    public void saveEntry(JournalEntry JournalEntry){

        journalEntryRepository.save(JournalEntry);

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        boolean removed=false;
        try {
            User user  =userService.findByUsername(username);
            removed=user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveNewUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured during deleting the entry : ", e);
        }
        return removed;
    }


}
