# 🏗️ Guía de Arquitectura - Total Warhammer App

## Visión General de la Arquitectura

Tu aplicación implementa **Clean Architecture** con las siguientes capas:

```
┌─────────────────────────────────────────┐
│         UI Layer (Composables)          │
│   - Screens, Components, States         │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      Presentation Layer (ViewModels)    │
│   - State Management, Business Logic    │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│       Domain Layer (UseCases)           │
│   - Business Rules, Pure Functions      │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      Data Layer (Repositories)          │
│   - Data Abstraction, Interfaces        │
└────────────────┬────────────────────────┘
                 │
        ┌────────┴────────┐
        │                 │
┌───────▼────────┐  ┌─────▼──────────┐
│  DataSources   │  │ Local Database │
│ (Remote API)   │  │  (Room, Store) │
└────────────────┘  └────────────────┘
```

## Estructura de Carpetas

```
app/src/main/java/com/totalwar/warhammer/
├── di/                          # Dependency Injection
│   ├── AppModule.kt            # Módulo de aplicación
│   ├── DatabaseModule.kt       # Módulo de base de datos
│   └── NetworkModule.kt        # Módulo de red
├── domain/                      # Capa de Dominio
│   └── usecase/
│       ├── GetAllFactionsUseCase.kt
│       ├── GetUnitByIdUseCase.kt
│       ├── GetFactionUnitsUseCase.kt
│       └── GetAbilityUseCase.kt
├── repository/                  # Capa de Datos (Interfaces)
│   ├── IFactionRepository.kt
│   ├── IUnitsRepository.kt
│   ├── IFactionUnitsRepository.kt
│   ├── IAbilityRepository.kt
│   ├── FactionRepository.kt     # Implementaciones
│   ├── UnitsRepository.kt
│   ├── FactionUnitsRepository.kt
│   └── AbilityRepository.kt
├── datasources/                 # Fuentes de Datos
│   ├── FactionDataSource.kt
│   ├── UnitsDataSource.kt
│   ├── FactionUnitsDataSource.kt
│   ├── AbilityDataSource.kt
│   ├── ArmyDataSource.kt
│   └── GameVersionDataSource.kt
├── database/                    # Base de Datos Local
│   ├── WarHammerDataBase.kt
│   ├── Converter.kt
│   └── army/
│       ├── Army.kt
│       └── ArmyDao.kt
├── viewmodels/                  # Capa de Presentación
│   ├── AppViewModel.kt
│   ├── faction/
│   │   ├── FactionViewModel.kt
│   │   └── FactionState.kt
│   ├── units/
│   │   ├── UnitViewModel.kt
│   │   └── UnitState.kt
│   ├── factionunits/
│   │   ├── FactionUnitsViewModel.kt
│   │   └── FactionUnitsState.kt
│   └── ability/
│       ├── AbilityViewModel.kt
│       └── AbilityState.kt
├── ui/                          # Capa de UI
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   ├── Shape.kt
│   │   └── Theme.kt
│   └── screen/
│       ├── AppScreen.kt
│       ├── components/
│       │   ├── tootlips/
│       │   │   ├── UnitAbilityTooltip.kt
│       │   │   ├── UnitAttributeTooltip.kt
│       │   │   ├── UnitMountTooltip.kt
│       │   │   └── content/
│       │   ├── Drawer.kt
│       │   └── Header.kt
│       ├── faction/
│       │   ├── FactionListScreen.kt
│       │   └── composables/
│       ├── units/
│       │   ├── UnitScreen.kt
│       │   └── composables/
│       ├── factionunits/
│       │   ├── UnitListScreen.kt
│       │   └── composables/
│       └── army/
│           ├── ArmiesListScreen.kt
│           └── composables/
├── util/                        # Utilidades
│   ├── Result.kt                # Result Pattern
│   ├── Constants.kt             # Constantes Centralizadas
│   ├── Logger.kt                # Sistema de Logging
│   ├── dispatcher/
│   │   ├── CoroutineDispatcherProvider.kt
│   │   └── DefaultDispatcherProvider.kt
│   └── [otros utils]
├── navigation/
│   ├── AppNavigator.kt
│   ├── AppScreens.kt
│   └── AppScreen.kt
├── services/
│   └── apollo/
│       └── Apollo.kt
└── settings/
    ├── Settings.kt
    └── SettingsSerializer.kt
```

## Flujo de Datos

### Ejemplo: Cargar Facciones

```
1. Usuario abre la app
   ↓
2. FactionListScreen carga
   ↓
3. FactionViewModel.findAllFactions() es llamado
   ↓
4. ViewModel llama a GetAllFactionsUseCase(gameVersion)
   ↓
5. UseCase llama a FactionRepository.getAllFactions(version)
   ↓
6. Repository llama a FactionDataSource.getFactions(version)
   ↓
7. DataSource hace una llamada GraphQL a través de Apollo
   ↓
8. Resultado se envuelve en Result.Success o Result.Error
   ↓
9. Repository retorna el resultado
   ↓
10. UseCase retorna el resultado (con Dispatcher.IO)
   ↓
11. ViewModel actualiza FactionState
   ↓
12. UI observa cambios y se recompone
```

## Patrones Implementados

### 1. **Result Pattern**
```kotlin
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable, val message: String?) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
```

**Uso:**
```kotlin
when (val result = useCase()) {
    is Result.Success -> handleSuccess(result.data)
    is Result.Error -> handleError(result.message)
    Result.Loading -> showLoadingIndicator()
}
```

### 2. **UseCase Pattern**
Encapsula la lógica de negocio separada de ViewModels:

```kotlin
class GetAllFactionsUseCase @Inject constructor(
    private val repository: IFactionRepository,
    private val dispatchers: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(gameVersion: String): Result<List<Faction>> {
        return withContext(dispatchers.io) {
            repository.getAllFactions(gameVersion)
        }
    }
}
```

### 3. **State Management**
Cada pantalla tiene su estado sellado:

```kotlin
sealed class FactionState {
    object Idle : FactionState()
    data class Error(val message: String) : FactionState()
    object Loading : FactionState()
    data class Success(...) : FactionState()
}
```

### 4. **Dependency Injection con Hilt**
Configuración centralizada:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFactionRepository(ds: FactionDataSource): IFactionRepository {
        return FactionRepository(ds)
    }
}
```

## Decisiones de Arquitectura

### ✅ Por qué esta arquitectura

| Decisión | Razón |
|----------|-------|
| Clean Architecture | Separación clara de responsabilidades |
| Result Pattern | Manejo seguro de errores |
| UseCases | Reutilización de lógica, testeable |
| Interfaces | Desacoplamiento, mockeable para tests |
| Hilt | Inyección automática, menos boilerplate |
| Coroutines | Concurrencia simple y eficiente |
| Compose | UI moderna, reactiva y declarativa |
| Room | Persistencia local eficiente |
| Apollo GraphQL | Type-safe, queries validadas |

## Cómo Añadir un Nuevo Feature

### Paso 1: Crear el UseCase
```kotlin
// domain/usecase/GetNewFeatureUseCase.kt
class GetNewFeatureUseCase @Inject constructor(
    private val repository: INewFeatureRepository,
    private val dispatchers: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(param: String): Result<NewFeatureData> {
        return withContext(dispatchers.io) {
            repository.getNewFeature(param)
        }
    }
}
```

### Paso 2: Crear la Interfaz del Repository
```kotlin
// repository/INewFeatureRepository.kt
interface INewFeatureRepository {
    suspend fun getNewFeature(param: String): Result<NewFeatureData>
}
```

### Paso 3: Implementar el Repository
```kotlin
// repository/NewFeatureRepository.kt
class NewFeatureRepository(
    private val dataSource: NewFeatureDataSource
) : INewFeatureRepository {
    override suspend fun getNewFeature(param: String): Result<NewFeatureData> {
        return safeApiCall {
            dataSource.getNewFeature(param) ?: throw NoSuchElementException()
        }
    }
}
```

### Paso 4: Crear el ViewModel
```kotlin
// viewmodels/newfeature/NewFeatureViewModel.kt
@HiltViewModel
class NewFeatureViewModel @Inject constructor(
    private val getNewFeatureUseCase: GetNewFeatureUseCase,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    private val _state = MutableStateFlow<NewFeatureState>(NewFeatureState.Idle)
    val state: StateFlow<NewFeatureState> = _state.asStateFlow()

    fun loadFeature(param: String) {
        viewModelScope.launch {
            _state.value = NewFeatureState.Loading
            val result = getNewFeatureUseCase(param)
            _state.value = when (result) {
                is Result.Success -> NewFeatureState.Success(result.data)
                is Result.Error -> NewFeatureState.Error(result.message ?: "Error")
                Result.Loading -> NewFeatureState.Loading
            }
        }
    }
}
```

### Paso 5: Crear el State
```kotlin
// viewmodels/newfeature/NewFeatureState.kt
sealed class NewFeatureState {
    object Idle : NewFeatureState()
    object Loading : NewFeatureState()
    data class Error(val message: String) : NewFeatureState()
    data class Success(val data: NewFeatureData) : NewFeatureState()
}
```

### Paso 6: Crear la UI
```kotlin
// ui/screen/newfeature/NewFeatureScreen.kt
@Composable
fun NewFeatureScreen(
    viewModel: NewFeatureViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState(NewFeatureState.Idle)

    LaunchedEffect(Unit) {
        viewModel.loadFeature("param")
    }

    when (val current = state) {
        is NewFeatureState.Error -> ErrorScreen(current.message)
        NewFeatureState.Idle, NewFeatureState.Loading -> LoadingScreen()
        is NewFeatureState.Success -> SuccessScreen(current.data)
    }
}
```

### Paso 7: Registrar en el DI
```kotlin
// di/AppModule.kt
@Provides
@Singleton
fun provideNewFeatureRepository(ds: NewFeatureDataSource): INewFeatureRepository {
    return NewFeatureRepository(ds)
}
```

## Testing

### Test del UseCase
```kotlin
@ExperimentalCoroutinesApi
class GetAllFactionsUseCaseTest {
    private lateinit var repository: IFactionRepository
    private lateinit var dispatchers: CoroutineDispatcherProvider
    private lateinit var useCase: GetAllFactionsUseCase

    @Before
    fun setup() {
        repository = mockk()
        dispatchers = mockk()
        useCase = GetAllFactionsUseCase(repository, dispatchers)
    }

    @Test
    fun `invoke returns success`() = runTest {
        val expected = listOf(mockk<FactionsQuery.Faction>())
        coEvery { repository.getAllFactions(any()) } returns Result.Success(expected)

        val result = useCase("gameVersion")

        assert(result is Result.Success)
        assert((result as Result.Success).data == expected)
    }
}
```

### Test del ViewModel
```kotlin
@ExperimentalCoroutinesApi
class FactionViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: GetAllFactionsUseCase
    private lateinit var dataStore: DataStore<Settings>
    private lateinit var viewModel: FactionViewModel

    @Before
    fun setup() {
        useCase = mockk()
        dataStore = mockk()
        viewModel = FactionViewModel(useCase, dataStore)
    }

    @Test
    fun `findAllFactions updates state to success`() = runTest {
        val factions = listOf(mockk<FactionsQuery.Faction>())
        coEvery { useCase(any()) } returns Result.Success(factions)
        coEvery { dataStore.data } returns flowOf(mockk<Settings>())

        viewModel.findAllFactions()

        val state = viewModel.factionList.value
        assert(state is FactionState.Success)
    }
}
```

## Mejores Prácticas

✅ **DO:**
- Usar interfaces para repositories
- Inyectar dependencias
- Manejar errores con Result pattern
- Usar UseCases para lógica de negocio
- Documentar código con KDoc
- Usar constantes centralizadas
- Loguear eventos importantes

❌ **DON'T:**
- No hardcodear valores
- No mezclar lógica de UI con lógica de negocio
- No capturar excepciones sin documentar
- No usar implementaciones concretas en DI
- No crear múltiples instancias de repositorios
- No escribir código sin comentarios complejos
- No ignorar warnings de lint

## Recursos Adicionales

- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Android Architecture Components](https://developer.android.com/jetpack/compose)
- [Hilt Dependency Injection](https://dagger.dev/hilt/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

**Versión**: 1.0
**Última actualización**: Noviembre 2025

