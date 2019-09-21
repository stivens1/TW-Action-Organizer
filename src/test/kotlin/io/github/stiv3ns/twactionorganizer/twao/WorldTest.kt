import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import io.kotlintest.tables.row
import io.github.stiv3ns.twactionorganizer.twao.World
import io.github.stiv3ns.twactionorganizer.twao.exceptions.BadDomainException
import io.github.stiv3ns.twactionorganizer.twao.exceptions.VillageNotFoundException
import io.github.stiv3ns.twactionorganizer.twao.villages.Village

class WorldTest : WordSpec({
    "World" When {
        "given bad domain" should {
            "throw an exception" {
                shouldThrow<BadDomainException> {
                    World("bad.domain")
                }
            }
        }


        "given good domain /*test domain viable until 2021*/" should {
            val world = World("plp6.plemiona.pl")
            /* if tests fail you should try changing domain since this one may be already closed */

            "set proper domain" {
                world.domain shouldBe "https://plp6.plemiona.pl"
            }

            "load proper settings" {
                with(world) {
                    maxNobleRange shouldBe 1000
                    nightBonusEndHour shouldBe 8
                    speed shouldBe 1.0
                }
            }

            "load proper Village.id" {
                forall(
                    row(Village(500, 499), 15),
                    row(Village(497, 506), 44),
                    row(Village(508, 499), 66)
                ) { village, id ->
                    world.fetchVillageID(village) shouldBe id
                }
            }

            "throw an exception when trying to handle non-existing village" {
                shouldThrow<VillageNotFoundException> {
                    world.fetchVillageID( Village(-1, -1) )
                }
            }
        }
    }
})
