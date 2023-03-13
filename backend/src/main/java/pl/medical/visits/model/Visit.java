//package pl.medical.visits.model;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.sql.Timestamp;
//
//@Data
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "visit")
//public class Visit {
//    @Id
//    private Long id;
//    private Office office;
//    private Patient patient;
//    private Doctor doctor;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="ECT")
//    private Timestamp timeStamp;
//    private String description;
//}
