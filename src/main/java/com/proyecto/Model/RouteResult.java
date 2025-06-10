package com.proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_result")
public class RouteResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    private RouteRequest request;

    @Column(name = "total_distance", nullable = false)
    private Integer totalDistance;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructor, getters y setters

    public RouteResult() {
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RouteRequest getRequest() {
        return request;
    }

    public void setRequest(RouteRequest request) {
        this.request = request;
    }

    public Integer getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Integer totalDistance) {
        this.totalDistance = totalDistance;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
