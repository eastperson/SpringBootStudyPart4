package com.ep.mreview.repository;

import com.ep.mreview.entity.Member;
import com.ep.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r"+i+"@bbbb.com")
                    .pw("1111")
                    .nickname("reviewer" + i).build();
            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDelteMember() {

        Long mid = 1L; // Member의 mid

        Member member = Member.builder().mid(mid).build();

        //기존
        //memberRepository.deleteById(mid);
        //reviewRepository.deleteByMember(member);

        //순서 주의
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

    }

}
