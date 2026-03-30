package com.parkingspaces.parkapi.web.controller;

import com.parkingspaces.parkapi.entity.Usuario;
import com.parkingspaces.parkapi.service.UsuarioService;
import com.parkingspaces.parkapi.web.dto.UsuarioCreateDto;
import com.parkingspaces.parkapi.web.dto.UsuarioResponseDto;
import com.parkingspaces.parkapi.web.dto.UsuarioSenhaDto;
import com.parkingspaces.parkapi.web.dto.mapper.UsuarioMapper;
import com.parkingspaces.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Usuários", description = "Endpoints relacionados a usuários do sistema")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Obter detalhes de um usuário por ID", description = "Endpoint para obter os detalhes de um usuário específico usando seu ID. Retorna um objeto JSON com as informações do usuário.",
            responses = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Obter uma lista de todos os usuários", description = "Endpoint para obter uma lista de todos os usuários registrados no sistema. Retorna um array JSON contendo os detalhes de cada usuário.",
            responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários obtida com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @GetMapping()
    public ResponseEntity<List<UsuarioResponseDto>>getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }

    @Operation(summary = "Criar um novo usuário", description = "Endpoint para criar um novo usuário no sistema. Requer um objeto JSON com os detalhes do usuário a ser criado.",
            responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Usuário e-mail ja cadastrados no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto usuarioDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUSuario(usuarioDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Atualizar a senha de um usuário", description = "Endpoint para atualizar a senha de um usuário específico. Requer o ID do usuário e um objeto JSON contendo a senha atual, nova senha e confirmação da nova senha.",
            responses = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados de entrada incorretos ou senhas não coincidem", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(),dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }
}
