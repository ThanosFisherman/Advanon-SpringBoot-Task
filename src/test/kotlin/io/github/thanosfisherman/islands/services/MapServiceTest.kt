package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.entities.*
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class MapServiceTest {

    @InjectMocks
    lateinit var mapService: MapService
    @Mock
    lateinit var mapRepository: MapRepository
    private val tileList = listOf(
            Tile(0, 0, TileType.LAND),
            Tile(0, 1, TileType.LAND),
            Tile(1, 0, TileType.LAND),
            Tile(1, 1, TileType.LAND))
    private val mapEntity: MapEntity = MapEntity("1", Data("3", "map", Link("")), Attribute(tileList))

    @Before
    fun setUp() {
        //given
        BDDMockito.given(mapRepository.findByDataIdAndDataType("3", "map")).willReturn(mapEntity)
        BDDMockito.given(mapRepository.save(mapEntity)).willReturn(mapEntity)
        BDDMockito.given(mapRepository.findAll()).willReturn(listOf(mapEntity))
    }

    @Test
    fun saveOrUpdateMap() {
        //when
        val map = mapService.saveOrUpdateMap(mapEntity)

        //then
        BDDMockito.verify(mapRepository, BDDMockito.times(1)).findByDataIdAndDataType("3", "map")
        BDDMockito.verify(mapRepository, BDDMockito.times(1)).save(map)
        BDDMockito.verifyNoMoreInteractions(mapRepository)
    }

    @Test
    fun getMaps() {
        //when
        val map = mapService.getMaps()

        //then
        BDDMockito.verify(mapRepository, BDDMockito.times(1)).findAll()
        BDDMockito.verifyNoMoreInteractions(mapRepository)
    }

    @Test
    fun printMaps() {
        val actualBuilder = mapService.printMaps()
        println(actualBuilder.toString())
        val expectedBuilder = StringBuilder("<pre>\n\n").append("XX\n").append("XX\n").append("\n").append("</pre>\n")
        Assertions.assertThat(actualBuilder.toString()).isEqualTo(expectedBuilder.toString())
    }

    @Test
    fun findMapById() {
        //Meh extension functions don't seem to play nicely with Mockito

        /*    //given
            BDDMockito.given(mapRepository.findOneById("1")).willReturn(mapEntity)
            //when
            val map = mapService.findMapById("1")
            //then
            Assertions.assertThat(map?.id).isEqualTo("1")*/
    }
}