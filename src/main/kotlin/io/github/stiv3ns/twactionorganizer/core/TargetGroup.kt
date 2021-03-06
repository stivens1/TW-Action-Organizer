package io.github.stiv3ns.twactionorganizer.core

import io.github.stiv3ns.twactionorganizer.core.assigners.AssignerType
import io.github.stiv3ns.twactionorganizer.core.villages.TargetVillage
import io.github.stiv3ns.twactionorganizer.core.villages.Village
import kotlin.math.roundToInt

data class TargetGroup(
    val name: String,
    val type: AssignerType,
    val villages: Collection<TargetVillage>
) {
    val villageCount = villages.size
    val totalAttackCount = villages.map { it.numberOfAttacks }.sum()

    val averagedCoordsAsVillage =
        Village(x = villages.map { it.x }.average().roundToInt(),
                y = villages.map { it.y }.average().roundToInt(),
                id = -1,
                ownerNickname = "--$name avg--")

    fun withDelayInMinutes(delay: Long): TargetGroup =
        copy(
            villages = villages.map { village ->
                village.withDelayInMinutes(delay)
            }
        )
}