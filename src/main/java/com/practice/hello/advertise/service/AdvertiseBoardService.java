package com.practice.hello.advertise.service;


import com.practice.hello.advertise.dto.AdvertiseBoardCreateDTO;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.repository.AdvertiseBoardRepository;
import com.practice.hello.advertise.repository.AdvertiseCommentRepository;
import com.practice.hello.advertise.repository.AdvertiseReplyRepository;
import com.practice.hello.image.entity.Image;
import com.practice.hello.image.repository.ImageRepository;
import com.practice.hello.image.service.S3Service;
import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service // Service annotation
@Slf4j
public class AdvertiseBoardService {


    private final AdvertiseBoardRepository advertiseBoardRepository;
    private final AdvertiseCommentRepository advertiseCommentRepository;

    private final MemberRepository memberRepository;
    private final AdvertiseReplyRepository advertiseReplyRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {
        // First delete all comments associated with the board

        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));


        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));

        if (!advertiseBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<AdvertiseComment> advertiseComments = advertiseCommentRepository.findByBoardId(id);
        for (AdvertiseComment advertiseComment : advertiseComments) {
            advertiseReplyRepository.deleteAllByAdvertiseCommentId(advertiseComment.getId());
        }
        // 이미지가 있으면 S3에서 삭제
        Image image = advertiseBoard.getImage();
        if (image != null) {
            String key = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
            s3Service.deleteFile(key);
            imageRepository.delete(image);
        }


        // Now delete all comments associated with the board
        advertiseCommentRepository.deleteAll(advertiseComments);

        // Finally, delete the board
        advertiseBoardRepository.deleteById(id);
        advertiseBoardRepository.flush();

    }
    public AdvertiseBoard saveBoard(AdvertiseBoardCreateDTO dto, String uId, MultipartFile file) {

        log.info("Creating post for user: {}", uId);
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            try {
                imageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Image image = imageUrl != null ? imageRepository.save(new Image(imageUrl)) : null;
        // 이미지가 dto에 없이 담겨서 왔다면 null로 db에 설정 아니라면 s3버켓에 넣고 설정

        AdvertiseBoard advertiseBoard = dto.toEntity(member,image);





        return  advertiseBoardRepository.save(advertiseBoard);
    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public AdvertiseBoard saveBoard(AdvertiseBoard advertiseBoard) {
        return advertiseBoardRepository.save(advertiseBoard);
    }


    public Optional<AdvertiseBoard> getBoardById(Long id){
        return advertiseBoardRepository.findById(id);
    }

    public List<AdvertiseBoard> readBoardAll(){
        return advertiseBoardRepository.findAll();
    }

    public void deleteBoard(Long id){
        advertiseBoardRepository.deleteById(id);
    }


    public AdvertiseBoard likeBoard(Long id) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        advertiseBoard.setLikeStatus(true); // Set like status to true
        return advertiseBoardRepository.save(advertiseBoard);
    }

    @Transactional
    public AdvertiseBoard unlikeBoard(Long id) {
        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        advertiseBoard.setLikeStatus(false); // Set like status to false
        return advertiseBoardRepository.save(advertiseBoard);
    }

    @Transactional
    public AdvertiseBoard updateBoard(Long id, AdvertiseBoardCreateDTO dto,String uId,MultipartFile file) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));

        AdvertiseBoard advertiseBoard = advertiseBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 해당하는 게시글 없어용"));

        if (!advertiseBoard .getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }


        Image image = advertiseBoard.getImage();
        if (file != null && !file.isEmpty()) {
            if (image != null) {
                String key = advertiseBoard.getImage().getImageUrl().substring(advertiseBoard.getImage().getImageUrl().lastIndexOf("/") + 1);
                s3Service.deleteFile(key);
                imageRepository.delete(image);
            }
            String imageUrl = null;
            try {
                imageUrl = s3Service.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            image = imageRepository.save(new Image(imageUrl));
        } else if (image != null) {
            imageRepository.delete(image);
            image = null;
        }
        advertiseBoard.update(dto.title(), dto.content());
        advertiseBoard.setImage(image);
        return advertiseBoardRepository.save(advertiseBoard);
    }

    public Page<AdvertiseBoard> readBoardAll(Pageable pageable) {
        return advertiseBoardRepository.findAll(pageable);
    }

}
