package org.miit.edf.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;
    @OneToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;
    private String text;
    private String type;
}
