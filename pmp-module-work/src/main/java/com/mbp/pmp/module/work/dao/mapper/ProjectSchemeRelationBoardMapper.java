package com.mbp.pmp.module.work.dao.mapper;

import com.kakarote.common.servlet.BaseMapper;
import com.mbp.pmp.module.work.domain.ProjectSchemeRelationBoard;
import org.apache.ibatis.annotations.Select;

public interface ProjectSchemeRelationBoardMapper extends BaseMapper<ProjectSchemeRelationBoard> {
    @Select("DELETE  FROM wk_project_scheme_relation_board WHERE id IN  (select id  from  ( SELECT b.id FROM wk_project_scheme_relation_board b LEFT JOIN wk_project_board_status bs ON bs.board_id = b.id WHERE bs.board_id IS NULL )as a) ")
    void deleteByStatusCountEmpty();
}
