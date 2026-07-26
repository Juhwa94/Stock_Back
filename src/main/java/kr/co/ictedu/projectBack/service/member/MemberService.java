package kr.co.ictedu.projectBack.service.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.projectBack.dao.member.MemberDao;
import kr.co.ictedu.projectBack.vo.MemberVO;

@Service
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    public void create(MemberVO vo) {
    	System.out.println("1");
    	
        if (memberDao.countById(vo.getId()) > 0) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        if (memberDao.countByEmail(vo.getEmail()) > 0) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        if (memberDao.countByNick(vo.getNick()) > 0) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }
        System.out.println("4");
        memberDao.insertMember(vo);
        System.out.println("5");
    }

    public int checkId(String id) {
        return memberDao.countById(id);
    }

    public int checkNick(String nick) {
        return memberDao.countByNick(nick);
	}

	public MemberVO getMemberById(String id) {
		return memberDao.getMemberById(id);
	}

	public int updateMember(MemberVO vo) {
		return memberDao.updateMember(vo);
	}
	
	@Transactional
	public boolean withdrawMember(int mnum) {
		int result = memberDao.deleteMember(mnum);
		return result > 0; 
    }
	
	// 회원 전체 조회
	public List<MemberVO> list(Map<String, String> map) {
		return memberDao.memberList(map);
	}
	
	public int totalCount(Map<String, String> map) {
		return memberDao.totalCount(map);
	}
	
	// 선택된 회원 등급 변경
	public int updateGrade(Map<String, Object> param) {
	    return memberDao.updateGrade(param);
	}
}





