package com.ep.mreview.service;

import com.ep.mreview.dto.MovieDTO;
import com.ep.mreview.dto.MovieImageDTO;
import com.ep.mreview.dto.PageRequestDTO;
import com.ep.mreview.dto.PageResultDTO;
import com.ep.mreview.entity.Movie;
import com.ep.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO); // 목록 처리

    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();
        List<MovieImageDTO> movieImageDTOList = movieImages.stream()
                .map(movieImage -> {
                    return MovieImageDTO.builder().imgName(movieImage.getImgName())
                            .path(movieImage.getPath())
                            .uuid(movieImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

    default Map<String,Object> dtoToEnity(MovieDTO movieDTO) { // Map 타입으로 반환

        Map<String,Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        // Movie Image DTO 처리
        if(imageDTOList != null && imageDTOList.size() > 0) {
            List<MovieImage> movieImageList = imageDTOList.stream().
                    map(movieImageDTO -> {

                        MovieImage movieImage = MovieImage.builder()
                                .path(movieImageDTO.getPath())
                                .imgName(movieImageDTO.getImgName())
                                .uuid(movieImageDTO.getUuid())
                                .movie(movie)
                                .build();
                        return movieImage;
                    }).collect(Collectors.toList());

            entityMap.put("imgList",movieImageList);
        }

        return entityMap;
    }

    MovieDTO getMovie(Long mno);
}