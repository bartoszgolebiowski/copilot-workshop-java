### 1. Dlaczego korzystać z testów?

Testy zapewniają:

1. **Wczesne wykrywanie błędów**: Pozwalają złapać problemy zanim trafią na produkcję
2. **Dokumentację kodu**: Testy pokazują jak używać funkcjonalności
3. **Pewność zmian**: Ułatwiają refaktoryzację i rozwój kodu
4. **Jakość**: Wymuszają lepszy design i modularność kodu

### Dlaczego używać AI do testowania?

1. **Szybkość**: Automatyczne generowanie testów jednostkowych
2. **Kompletność**: AI pamięta o różnych przypadkach testowych
3. **Spójność**: Generuje testy zgodne z konwencjami
4. **Oszczędność czasu**: Mniej pisania boilerplate code

#### 2. Przykład

Przykładowy kontroler z kilkoma endpointami, które można przetestować:

```java
// UserController.java
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        UserDto created = userService.create(userDto);
        return ResponseEntity
            .created(URI.create("/api/users/" + created.getId()))
            .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {
        if (!id.equals(userDto.getId())) {
            throw new IllegalArgumentException("ID mismatch");
        }
        return ResponseEntity.ok(userService.update(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

Przykładowe testy jednostkowe dla kontrolera wygenerowane za pomoca Copilota:

```java
// UserControllerTest.java
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserById_WhenUserExists_ReturnsUser() throws Exception {
        // Given
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "John Doe", "john@example.com");
        when(userService.findById(userId)).thenReturn(Optional.of(userDto));

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserById_WhenUserDoesNotExist_Returns404() throws Exception {
        // Given
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_WithValidData_ReturnsCreated() throws Exception {
        // Given
        UserDto userDto = new UserDto(null, "John Doe", "john@example.com");
        UserDto createdDto = new UserDto(1L, "John Doe", "john@example.com");
        when(userService.existsByEmail(anyString())).thenReturn(false);
        when(userService.create(any(UserDto.class))).thenReturn(createdDto);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/1"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createUser_WithDuplicateEmail_ReturnsBadRequest() throws Exception {
        // Given
        UserDto userDto = new UserDto(null, "John Doe", "john@example.com");
        when(userService.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_WithValidData_ReturnsOk() throws Exception {
        // Given
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "John Doe Updated", "john@example.com");
        when(userService.update(any(UserDto.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    void deleteUser_ReturnsNoContent() throws Exception {
        // Given
        Long userId = 1L;
        doNothing().when(userService).delete(userId);

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
```
