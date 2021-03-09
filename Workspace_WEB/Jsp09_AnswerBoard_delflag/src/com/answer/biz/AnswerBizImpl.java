package com.answer.biz;

import java.util.List;

import com.answer.dao.AnswerDao;
import com.answer.dao.AnswerDaoImpl;
import com.answer.dto.AnswerDto;


public class AnswerBizImpl implements AnswerBiz {
	
	private AnswerDao dao = new AnswerDaoImpl(); // 그냥 이렇게 했다~
	

	@Override
	public List<AnswerDto> selectList() {
		// TODO Auto-generated method stub
		return dao.selectList();
	}

	@Override
	public AnswerDto selectOne(int boardno) {
		// TODO Auto-generated method stub
		return dao.selectOne(boardno);
	}

	@Override
	public boolean boardInsert(AnswerDto dto) {
		// TODO Auto-generated method stub
		return dao.boardInsert(dto);
	}

	@Override
	public boolean boardUpdate(AnswerDto dto) {
		// TODO Auto-generated method stub
		return dao.boardUpdate(dto);
	}

	@Override
	public boolean boardDelete(int boardno) {
		// TODO Auto-generated method stub
		return dao.boardDelete(boardno);
	}

	@Override
	public int answerProc(AnswerDto dto) {
		
		//원래는 커밋과 롤백의 트랜잭션을 다오가 아닌 비즈니스에서 해준다. 지금은 그냥 함 
		//트랜잭션처리 
		//business logic (service logic)에서 트랜잭션 처리 
		//트랜잭션 transaction 최소 작업단위 둘다할거면 둘다할거고 커밋 롤백 둘중하나만 안될거면 안할거임 
		int update = dao.answerUpdate(dto.getBoardno());
		int insert = dao.answerInsert(dto);
		/*
		if (insert > 0 && update > 0) {
            return insert + update;
        }
        */
		return update + insert;
	}

}
