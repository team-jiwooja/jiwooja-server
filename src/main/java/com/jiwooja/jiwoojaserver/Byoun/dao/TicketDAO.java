package com.jiwooja.jiwoojaserver.Byoun.dao;

import com.jiwooja.jiwoojaserver.Byoun.dto.TicketDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDAO {

    private final JdbcTemplate jdbcTemplate;

    public TicketDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TicketDTO> searchMemTicket(String nickname) {
        String query = "SELECT tt.ticket_num_pk, api.train_type, api.train_num,api.train_date, api.starting_subway, api.ending_subway, seat.seat_name, tt.totalprice, mem.nickname"
                + " FROM train_ticket tt"
                + " JOIN seat_table seat ON tt.seat_code = seat.seat_code"
                + " JOIN train_table train ON seat.train_code = train.train_code"
                + " JOIN train_API api ON train.train_num = api.train_num"
                + " JOIN user_info mem ON mem.usernum_pk = tt.usernum_pk"
                + " WHERE mem.nickname = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> new TicketDTO(
                rs.getInt(1), //ticketNum
                rs.getString(2), //trainName
                rs.getInt(3), // trainNum
                rs.getInt(4), // trainHoNum
                rs.getInt(5), // date
                rs.getString(6), // startingSubway
                rs.getString(7), // endingSubway
                rs.getString(8), // seatName
                rs.getInt(9), // price
                rs.getString(10) // nickname 추가
        ), nickname);
    }

    public boolean refundMemTicket(String ticketNum, int trainNum) {
        String query1 = "UPDATE train_table SET train_seat_qty = train_seat_qty + 1 WHERE train_num = ?";
        String query2 = "DELETE FROM seat_table WHERE seat_code = (SELECT seat_code FROM train_ticket WHERE ticket_num_pk = ?)";

        int updateCount1 = jdbcTemplate.update(query1, trainNum);
        int updateCount2 = jdbcTemplate.update(query2, ticketNum);

        return updateCount1 == 1 && updateCount2 == 1;
    }

    public List<TicketDTO> searchNoMemTicket(String nickname) {
        String query = "SELECT tt.ticket_num, api.train_type, api.train_num, api.train_date, api.starting_subway, api.ending_subway, seat.seat_name, tt.total_price, mem.nickname"
                + " FROM train_unmember_ticket tt"
                + " JOIN seat_table seat ON tt.seat_code = seat.seat_code"
                + " JOIN train_table train ON seat.train_code = train.train_code"
                + " JOIN train_API api ON train.train_num = api.train_num"
                + " JOIN non_mem_info mem ON mem.no_mem_pk = tt.no_mem_pk"
                + " WHERE mem.nickname = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> new TicketDTO(
                rs.getInt(1), //ticketNum
                rs.getString(2), //trainName
                rs.getInt(3), // trainNum
                rs.getInt(4), // trainHoNum
                rs.getInt(5), // date
                rs.getString(6), // startingSubway
                rs.getString(7), // endingSubway
                rs.getString(8), // seatName
                rs.getInt(9), // price
                rs.getString(10) // nickname 추가
        ), nickname);
    }

    public int refundNoMemTicket(String nomomNum, String ticketNum, int trainNum) {
        String query1 = "UPDATE train_table SET train_seat_qty = train_seat_qty + 1 WHERE train_num = ?";
        String query2 = "DELETE FROM seat_table WHERE seat_code = (SELECT seat_code FROM train_unmember_ticket WHERE ticket_num = ?)";
        String query3 = "DELETE FROM non_mem_info WHERE no_mem_pk = ?";

        int updateCount1 = jdbcTemplate.update(query1, trainNum);
        int updateCount2 = jdbcTemplate.update(query2, ticketNum);
        int updateCount3 = jdbcTemplate.update(query3, nomomNum);

        if (updateCount1 == 1 && updateCount2 == 1 && updateCount3 == 1) {
            return 2;
        } else if (updateCount1 == 1 && updateCount2 == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public int refundNoMemTicket_search(String ticketNum) {
        String query = "SELECT * FROM non_mem_info us "
                + "JOIN train_unmember_ticket tt ON us.no_mem_pk = tt.no_mem_pk "
                + "JOIN seat_table seat ON tt.seat_code = seat.seat_code "
                + "JOIN train_table train ON seat.train_code = train.train_code "
                + "JOIN train_api api ON train.train_num = api.train_num "
                + "WHERE tt.ticket_num = ?";
        String query1 = "UPDATE train_table SET train_seat_qty = train_seat_qty + 1 WHERE train_num = ?";
        String query2 = "DELETE FROM seat_table WHERE seat_code = (SELECT seat_code FROM train_unmember_ticket WHERE ticket_num = ?)";
        String query3 = "DELETE FROM non_mem_info WHERE no_mem_pk = ?";

        List<String> trainNums = jdbcTemplate.queryForList(query, String.class, ticketNum);
        if (trainNums.isEmpty()) {
            return 0;
        }

        String trainNum = trainNums.get(0);
        int updateCount1 = jdbcTemplate.update(query1, trainNum);
        int updateCount2 = jdbcTemplate.update(query2, ticketNum);
        int updateCount3 = jdbcTemplate.update(query3, trainNum);

        if (updateCount1 == 1 && updateCount2 == 1 && updateCount3 == 1) {
            return 2;
        } else if (updateCount1 == 1 && updateCount2 == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
