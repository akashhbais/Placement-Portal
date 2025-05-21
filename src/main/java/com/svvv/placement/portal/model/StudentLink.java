/**
 * 
 */
package com.svvv.placement.portal.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Bais
 *
 */
@Entity
@Table(name = "student_links", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "type"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LinkType type;

    @Column(nullable = false)
    private String url;

    public enum LinkType {
        GITHUB, LINKEDIN, PORTFOLIO, OTHER
    }
}

