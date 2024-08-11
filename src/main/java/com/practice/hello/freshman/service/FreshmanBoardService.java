package com.practice.hello.freshman.service;


import com.practice.hello.freshman.dto.FreshmanBoardCreateDTO;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.repository.FreshmanBoardRepository;
import com.practice.hello.freshman.repository.FreshmanCommentRepository;
import com.practice.hello.freshman.repository.FreshmanReplyRepository;
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
public class FreshmanBoardService {


    private final FreshmanBoardRepository freshmanRepository;
    private final FreshmanCommentRepository freshmanCommentRepository;

    private final MemberRepository memberRepository;
    private final FreshmanReplyRepository freshmanReplyRepository;

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public void deleteBoardAndAdjustIds(Long id, String uId) {

        // First delete all comments associated with the board
        // First, delete all replies associated with each comment of the board
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람없어용"));

        Freshman freshmanBoard = freshmanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));
        if (!freshmanBoard.getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 삭제 가능해용");
        }
        // First, delete all replies associated with each comment of the board
        List<FreshmanComment> freshmanComments = freshmanCommentRepository.findByBoardId(id);
        for (FreshmanComment freshmanComment : freshmanComments) {
            freshmanReplyRepository.deleteAllByFreshmanCommentId(freshmanComment.getId());
        }

        Image image = freshmanBoard.getImage();
        if (image != null) {
            String key = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
            s3Service.deleteFile(key);
            imageRepository.delete(image);
        }

        // Now delete all comments associated with the board
        freshmanCommentRepository.deleteAll(freshmanComments);

        // Finally, delete the board
        freshmanRepository.deleteById(id);
        freshmanRepository.flush();

    }
    public Freshman saveBoard(FreshmanBoardCreateDTO dto,String uId, MultipartFile file) {


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

        Freshman freshmanBoard = dto.toEntity(member,image);
        return freshmanRepository.save(freshmanBoard);

    }


    //update시에는 entity 형태로 받아서 저장해야 한다
    public Freshman saveBoard(Freshman freshman) {
        return freshmanRepository.save(freshman);
    }


    public Optional<Freshman> getBoardById(Long id){
        return freshmanRepository.findById(id);
    }

    public List<Freshman> readBoardAll(){
        return freshmanRepository.findAll();
    }

    public void deleteBoard(Long id){
        freshmanRepository.deleteById(id);
    }


    public Freshman likeBoard(Long id) {
        Freshman freshman = freshmanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freshman.setLikeStatus(true); // Set like status to true
        return freshmanRepository.save(freshman);
    }

    @Transactional
    public Freshman unlikeBoard(Long id) {
        Freshman freshman = freshmanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        freshman.setLikeStatus(false); // Set like status to false
        return freshmanRepository.save(freshman);
    }

    @Transactional
    public Freshman updateBoard(Long id, FreshmanBoardCreateDTO dto,String uId, MultipartFile file) {
        Member member = memberRepository.findByUid(uId)
                .orElseThrow(() -> new IllegalArgumentException("이 계정에 해당하는 사람없어용"));
        Freshman freshmanBoard = freshmanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 게시글 없어용"));

        if (!freshmanBoard .getMember().getUid().equals(uId)) {
            throw new IllegalArgumentException("본인 게시글만 수정 가능해용");
        }

        Image image = freshmanBoard.getImage();
        if (file != null && !file.isEmpty()) {
            if (image != null) {
                String key = freshmanBoard.getImage().getImageUrl().substring(freshmanBoard.getImage().getImageUrl().lastIndexOf("/") + 1);
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

        freshmanBoard.update(dto.title(), dto.content());
        freshmanBoard.setImage(image);
        return freshmanRepository.save(freshmanBoard);
    }

    public Page<Freshman> readBoardAll(Pageable pageable) {
        return freshmanRepository.findAll(pageable);
    }

}
