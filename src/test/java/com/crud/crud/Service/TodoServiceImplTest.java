package com.crud.crud.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Enums.StateEnum;
import com.crud.crud.Repository.TodoRepository;
import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.RequestRecord.UpdateTodoRecord;
import com.crud.crud.config.ContainerBaseTest;
import com.crud.crud.config.MongoDBTestConfig;
import com.crud.crud.Exceptions.TodoNotFoundException;

@ExtendWith(MockitoExtension.class)
@Import(MongoDBTestConfig.class)
public class TodoServiceImplTest extends ContainerBaseTest{
    
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    public void testCreateTodo() {
        // Arrange
        String expectedTitle = "Comprar víveres";
        String expectedDescription = "Comprar leche, pan y huevos";
        String expectedEndDate = LocalDateTime.now().toString();
        String expectedCreatedAt = LocalDateTime.now().toString();
        CreateTodoRecord createTodoRecord = new CreateTodoRecord(expectedTitle, expectedDescription, expectedCreatedAt, expectedEndDate);
        
        TodoEntity expectedTodo = new TodoEntity(expectedTitle, expectedDescription, expectedEndDate, expectedCreatedAt);
        
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(expectedTodo);

        // Act
        String result = todoService.createTodo(createTodoRecord);

        // Assert
        assertNotNull(result, "El resultado no puede ser nulo");
        assertEquals("Todo created successfully", result, "El mensaje de resultado no coincide");

        // Captura el argumento con el que se llamó al save()
        ArgumentCaptor<TodoEntity> captor = ArgumentCaptor.forClass(TodoEntity.class);
        verify(todoRepository).save(captor.capture());

         // Aquí captura el argumento
        
        // Verifica que el argumento capturado es el esperado
        TodoEntity saved = captor.getValue();

        assertEquals(expectedTitle, saved.getTitle(), "El título del TODO no coincide");
        assertEquals(expectedDescription, saved.getDescription(), "La descripción del TODO no coincide");


    }

    @Test
    public void testUpdateTodo() {
        // Arrange
        String id = "67f7128b43ca2965eb3df6aa";
        String expectedTitle = "Comprar víveres";
        String expectedDescription = "Comprar leche, pan y huevos";
        boolean expectedCompleted = true;
        String expectedEndDate = LocalDateTime.now().toString();
        String expectedCreatedAt = LocalDateTime.now().toString();

        UpdateTodoRecord updateTodoRecord = new UpdateTodoRecord(expectedTitle, expectedDescription, StateEnum.COMPLETED, expectedEndDate);

        TodoEntity expectedTodo = new TodoEntity(id, expectedTitle, expectedDescription, expectedCreatedAt, expectedEndDate, StateEnum.COMPLETED);
        when(todoRepository.findById(id)).thenReturn(Optional.of(expectedTodo));
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(expectedTodo);

        // Act
        String result = todoService.updateTodo(id, updateTodoRecord);


        assertNotNull(result, "El resultado no puede ser nulo");
        assertEquals("Todo updated successfully", result, "El mensaje de resultado no coincide");

        ArgumentCaptor<TodoEntity> captor = ArgumentCaptor.forClass(TodoEntity.class);
        verify(todoRepository).save(captor.capture());

        TodoEntity saved = captor.getValue();
        assertEquals(expectedTitle, saved.getTitle(), "El título del TODO no coincide");
        assertEquals(expectedDescription, saved.getDescription(), "La descripción del TODO no coincide");
        assertEquals(expectedCompleted, saved.getState().equals(StateEnum.COMPLETED), "El estado del TODO no coincide");
        assertEquals(id, saved.getId(), "El ID del TODO no coincide");
    }

    @Test
    public void testUpdateTodoNotFound() {
        // Arrange
        String id = "67f7128b43ca2965eb3df6aa";
        String expectedEndDate = LocalDateTime.now().toString();
        UpdateTodoRecord updateTodoRecord = new UpdateTodoRecord("Nuevo Título", "Nueva Descripción", StateEnum.COMPLETED, expectedEndDate);

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> {
            todoService.updateTodo(id, updateTodoRecord);
        });
        verify(todoRepository, times(0)).save(any(TodoEntity.class));
    }

    @Test
    public void testDeleteTodo() {
        // Arrange
        String id = "67f7128b43ca2965eb3df6aa";
        String expectedMessage = "Todo deleted successfully";

        when(todoRepository.existsById(id)).thenReturn(true);

        // Act
        String result = todoService.deleteTodo(id);

        // Assert
        assertEquals(expectedMessage, result, "El mensaje de resultado no coincide");
        verify(todoRepository, times(1)).deleteById(id);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(todoRepository).deleteById(captor.capture());

        String capturedId = captor.getValue();
        assertEquals(id, capturedId, "El ID del TODO no coincide");
    }

    @Test
    public void testDeleteTodoNotFound() {
        // Arrange
        String id = "67f7128b43ca2965eb3df6aa";

        when(todoRepository.existsById(id)).thenReturn(false);

        assertThrows(TodoNotFoundException.class, () -> {
            todoService.deleteTodo(id);
        });
        verify(todoRepository, times(0)).deleteById(id);
    }


    
    
}
