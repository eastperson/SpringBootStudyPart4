package com.ep.mreview.repository;

import com.ep.mreview.entity.Movie;
import com.ep.mreview.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
