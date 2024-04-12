package com.mohamedsamir1495.eventbookingsystem.domain.event;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EventSpecifications {

    private EventSpecifications(){}

    public static Specification<Event> hasName(String name) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Event> hasStartDate(LocalDate startDate) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> startDate == null ? null : cb.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    public static Specification<Event> hasEndDate(LocalDate endDate) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> endDate == null ? null : cb.lessThanOrEqualTo(root.get("date"), endDate);
    }

    public static Specification<Event> hasCategory(Category category) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> category == null ? null : cb.equal(root.get("category"), category.toString().toUpperCase());
    }
}
