package com.jiwooja.jiwoojaserver.Byoun.trainSeat;

import com.jiwooja.jiwoojaserver.Byoun.entity.Train;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.math.BigInteger;


public interface TrainSeatDao extends CrudRepository<Train, Integer> {

    @Query(value = "SELECT * FROM train_table WHERE train_num = :trainNum AND train_code" +
            " = :trainCode AND train_ho_num = :trainHoNum AND train_seat_qty = :trainHoType", nativeQuery = true)
    Train chkTrainTable(@Param("trainNum") int trainNum, @Param("trainCode") int trainCode,
                        @Param("trainHoNum") String trainHoNum, @Param("trainHoType") String trainHoType);

    @Modifying
    @Query(value = "INSERT INTO train_table (train_num, train_code, train_ho_num," +
            "train_ho_type, train_seat_qty) VALUES (:trainNum, :trainCode, :trainHoNum," +
            ":trainHoType, :trainHoQty)", nativeQuery = true)
    void setTrainHo(@Param("trainNum") int trainNum, @Param("trainCode") int trainCode,
                    @Param("trainHoNum") String trainHoNum, @Param("trainHoType") String trainHoType, @Param("trainHoQty") String trainHoQty);
    @Modifying
    @Query(value = "UPDATE train_table SET train_seat_qty = train_seat_qty - 1" +
            " WHERE train_code = :trainCode", nativeQuery = true)
    void setSeatDown(@Param("trainCode") int trainCode);

    @Modifying
    @Query(value = "INSERT INTO train_seat_table (train_code, seat_code, seat_name, seat_type, seat_price)" +
            " VALUES (:trainCode, :seatCode, :seatName, :seatType, :seatPrice)", nativeQuery = true)
    void setSeat(@Param("trainCode") int trainCode, @Param("seatCode") String seatCode,
                 @Param("seatName") String seatName, @Param("seatType") String seatType,
                 @Param("seatPrice") int seatPrice);

    @Transactional
    @Modifying
    @Query(value = "UPDATE train_table SET train_seat_qty = train_seat_qty + 1 WHERE train_num = ?1", nativeQuery = true)
    void increaseSeatQty(int trainNum);

    /*ticket 정보가 있다면 이 코드
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM train_seat_table WHERE seat_code = (SELECT seat_code FROM train_ticket WHERE ticket_num_pk = ?1)", nativeQuery = true)
    void deleteSeatByTicketNum(String seat_code);*/

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM train_seat_table WHERE seat_code = ?1", nativeQuery = true)
    void deleteSeatByTicketNum(String seat_code);

    @Modifying
    @Query(value = "UPDATE train_table SET train_seat_qty = train_seat_qty - ?2 WHERE train_code = ?1", nativeQuery = true)
    void decreaseSeatQtyByTrainCode(int trainCode, int decrementValue);

    @Query(value = "SELECT COUNT(*) FROM train_table WHERE train_code = :trainCode", nativeQuery = true)
    BigInteger countByTrainCode(@Param("trainCode") int trainCode);

}
