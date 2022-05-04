package com.tisory.jaimemin.jwt.dto;

import lombok.*;

/**
 * @author jaime
 * @title TokenDto
 * @see\n <pre>
 * </pre>
 * @since 2022-05-04
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;
}
