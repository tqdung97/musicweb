package com.example.mp3backend.service;

import com.example.mp3backend.entity.Singer;
import com.example.mp3backend.entity.User;
import com.example.mp3backend.exception.BadRequestException;
import com.example.mp3backend.exception.NotFoundException;
import com.example.mp3backend.repository.SingerRepository;
import com.example.mp3backend.repository.UserRepository;
import com.example.mp3backend.request.UpsertSingerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class SingerService {
    @Autowired
    SingerRepository singerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Singer> getAllSinger() {
         if (singerRepository.findAll().isEmpty()){
             throw new NotFoundException("Chưa có ca sĩ nào tồn tại");
         }
         return singerRepository.findAll();
    }

    public Singer getSingerById(Long id) {
        return singerRepository.findById(id).orElseThrow(() -> {
            throw  new NotFoundException("Không tìm thấy ca sĩ với id " + id);
        });
    }
    @Transactional
    public Singer createSinger(UpsertSingerRequest request) {

        Optional<Singer> singerOptional = singerRepository.findByName(request.getName());
        if(singerOptional.isPresent()){
            Singer singer = singerOptional.get();
            if(singer.getName().equals(request.getName())){
               throw new  BadRequestException("Tên ca sĩ đã được khởi tạo");
            }

        }
         Singer singer = Singer.builder()
                 .name(request.getName())
                 .birtday(request.getBirthday())
                 .country(request.getCountry())
                 .description(request.getDescription())
                 .image(request.getImage())
                 .build();
       return singerRepository.save(singer);
    }
    @Transactional
    public Singer updateSinger(Long id, UpsertSingerRequest request) {
        Singer singer = singerRepository.findById(id).orElseThrow(()->{
            throw new NotFoundException("Không tìm thấy ca sĩ với id" + id);
        });
        singer.setDescription(request.getDescription());
        singer.setBirtday(request.getBirthday());
        singer.setCountry(request.getCountry());
        singer.setImage(request.getImage());
        return singerRepository.save(singer);
    }
    @Transactional
    public void deleteSinger(Long id) {
        Singer singer = singerRepository.findById(id).orElseThrow(()->{
            throw new NotFoundException("Không tìm thấy ca sĩ với id" + id);
        });
        singerRepository.delete(singer);
    }
}
