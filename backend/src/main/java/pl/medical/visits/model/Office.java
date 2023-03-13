//package pl.medical.visits.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "office")
//public class Office {
//    @Id
//    private long id;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
//    private Doctor doctor;
//
//    private String officeNr;
//
//    private String floor;
//}
