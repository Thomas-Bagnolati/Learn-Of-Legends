package com.bagnolati.learnoflegends.feature.champions.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.icon.LolIcons
import com.bagnolati.learnoflegends.core.ui.preview.ThemePreviews
import com.bagnolati.learnoflegends.core.ui.theme.LolTheme
import com.bagnolati.learnoflegends.core.ui.theme.spacing
import com.bagnolati.learnoflegends.feature.champions.R
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ALPHABETIC
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ARMOR
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ARMOR_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ATTACK_DAMAGE
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ATTACK_DAMAGE_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ATTACK_RANGE
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ATTACK_SPEED
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.ATTACK_SPEED_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.CRIT
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.CRIT_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.HP
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.HP_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.HP_REGEN
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.HP_REGEN_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MAGIC_RESIST
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MAGIC_RESIST_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MOVE_SPEED
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MP
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MP_LVL
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MP_REGEN
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder.MP_REGEN_LVL


@Composable
internal fun ChampionOrderColumn(
    modifier: Modifier = Modifier,
    opened: Boolean,
    selectedOrder: ChampionOrder,
    expandFab: Boolean,
    onClickChampionOrder: (ChampionOrder) -> Unit,
    onClickFloatingActionButton: () -> Unit
) {
    val orders = ChampionOrder.values().asList()
    val lazyListState = rememberLazyListState()
    var lastFirstVisibleItemIndex by remember { mutableIntStateOf(0) }

    @StringRes val stringResFab =
        if (opened) R.string.order_floating_action_button_close
        else R.string.order_floating_action_button

    val icon = if (opened) LolIcons.Close else LolIcons.Filter

    val contentColor =
        if (opened) MaterialTheme.colorScheme.onErrorContainer
        else MaterialTheme.colorScheme.onPrimaryContainer

    val containerColor =
        if (opened) MaterialTheme.colorScheme.errorContainer
        else MaterialTheme.colorScheme.primaryContainer

    LaunchedEffect(opened) {
        if (opened) {
            // Restore position when open
            lazyListState.scrollToItem(lastFirstVisibleItemIndex)
        } else {
            // Save it when close
            lastFirstVisibleItemIndex = lazyListState.firstVisibleItemIndex
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        if (opened) {
            LazyColumnChampionOrders(
                modifier = Modifier.weight(1f),
                orders = orders,
                selectedOrder = selectedOrder,
                onClick = onClickChampionOrder,
                state = lazyListState
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        ExtendedFloatingActionButton(
            modifier = Modifier.padding(
                bottom = MaterialTheme.spacing.floatingActionButton,
                end = MaterialTheme.spacing.floatingActionButton
            ),
            onClick = onClickFloatingActionButton,
            expanded = expandFab,
            text = { Text(text = stringResource(stringResFab)) },
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.order_floating_action_button_description)
                )
            },
            contentColor = contentColor,
            containerColor = containerColor
        )
    }
}


@Composable
internal fun LazyColumnChampionOrders(
    modifier: Modifier = Modifier,
    orders: List<ChampionOrder>,
    selectedOrder: ChampionOrder,
    onClick: (ChampionOrder) -> Unit,
    state: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.floatingActionButton
        ),
        state = state
    ) {
        items(
            items = orders,
            key = { it.ordinal }
        ) {
            ChampionOrderItem(
                championOrder = it,
                isSelected = selectedOrder.name == it.name,
                onClick = { onClick(it) }
            )
        }
    }
}

@Composable
private fun ChampionOrderItem(
    championOrder: ChampionOrder,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors =
        if (isSelected) ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
        else ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.inverseSurface,
            contentColor = MaterialTheme.colorScheme.inverseOnSurface,
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = stringResource(id = championOrder.shortName),
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@ThemePreviews
@Composable
private fun ChampionOrderSectionPreview() {
    LolTheme {
        Surface {
            ChampionOrderColumn(
                opened = true,
                selectedOrder = ALPHABETIC,
                expandFab = true,
                onClickChampionOrder = {},
                onClickFloatingActionButton = {}
            )
        }
    }
}

// TODO : May move refactor this part, may move this to an other layer / file ?

/**
 * Data representation of Order of a [Champion].
 */
enum class ChampionOrder(
    @StringRes val completeName: Int,
    @StringRes val shortName: Int
) {
    ALPHABETIC(completeName = R.string.order_alphabetic_complete, shortName = R.string.order_alphabetic_short),
    HP(completeName = R.string.order_hp_complete, shortName = R.string.order_hp_short),
    HP_LVL(completeName = R.string.order_hp_lvl_complete, shortName = R.string.order_hp_lvl_short),
    MP(completeName = R.string.order_mp_complete, shortName = R.string.order_mp_short),
    MP_LVL(completeName = R.string.order_mp_lvl_complete, shortName = R.string.order_mp_lvl_short),
    MOVE_SPEED(completeName = R.string.order_move_speed_complete, shortName = R.string.order_move_speed_short),
    ARMOR(completeName = R.string.order_ar_complete, shortName = R.string.order_ar_short),
    ARMOR_LVL(completeName = R.string.order_ar_lvl_complete, shortName = R.string.order_ar_lvl_short),
    MAGIC_RESIST(completeName = R.string.order_rm_complete, shortName = R.string.order_rm_short),
    MAGIC_RESIST_LVL(completeName = R.string.order_rm_lvl_complete, shortName = R.string.order_rm_lvl_short),
    ATTACK_RANGE(completeName = R.string.order_attack_range_complete, shortName = R.string.order_attack_range_short),
    HP_REGEN(completeName = R.string.order_hp_regen_complete, shortName = R.string.order_hp_regen_short),
    HP_REGEN_LVL(completeName = R.string.order_hp_regen_lvl_complete, shortName = R.string.order_hp_regen_lvl_short),
    MP_REGEN(completeName = R.string.order_mp_regen_complete, shortName = R.string.order_mp_regen_short),
    MP_REGEN_LVL(completeName = R.string.order_mp_regen_lvl_complete, shortName = R.string.order_mp_regen_lvl_short),
    CRIT(completeName = R.string.order_crit_complete, shortName = R.string.order_crit_short),
    CRIT_LVL(completeName = R.string.order_crit_lvl_complete, shortName = R.string.order_crit_lvl_short),
    ATTACK_DAMAGE(completeName = R.string.order_ad_complete, shortName = R.string.order_ad_short),
    ATTACK_DAMAGE_LVL(completeName = R.string.order_ad_lvl_complete, shortName = R.string.order_ad_lvl_short),
    ATTACK_SPEED(completeName = R.string.order_as_complete, shortName = R.string.order_as_short),
    ATTACK_SPEED_LVL(completeName = R.string.order_as_lvl_complete, shortName = R.string.order_as_lvl_short),
}

/**
 * Retrieve stat to display from [ChampionOrder] as String.
 *
 * @param order The [ChampionOrder] to know witch value to return.
 * @param indexOnList The index of champion on list to retrieve the [ALPHABETIC] position,
 * set it at null if don't care about ALPHABETIC stat.
 */
internal fun Champion.getStatByOrderAsString(order: ChampionOrder, indexOnList: Int? = null): String {
    return when (order) {
        ALPHABETIC -> positionFromIndexAsText(indexOnList) ?: ""
        HP -> stats.hp.toString()
        HP_LVL -> stats.hpPerLevel.toString()
        MP -> stats.mp.numberToString()
        MP_LVL -> stats.mpPerLevel.numberToString()
        MOVE_SPEED -> stats.moveSpeed.toString()
        ARMOR -> stats.armor.toString()
        ARMOR_LVL -> stats.armorPerLevel.numberToString()
        MAGIC_RESIST -> stats.spellBlock.toString()
        MAGIC_RESIST_LVL -> stats.spellBlockPerLevel.numberToString()
        ATTACK_RANGE -> stats.attackRange.toString()
        HP_REGEN -> stats.hpRegen.numberToString()
        HP_REGEN_LVL -> stats.hpRegenPerLevel.numberToString()
        MP_REGEN -> stats.mpRegen.numberToString()
        MP_REGEN_LVL -> stats.mpRegenPerLevel.numberToString()
        CRIT -> stats.crit.toString()
        CRIT_LVL -> stats.critPerLevel.toString()
        ATTACK_DAMAGE -> stats.attackDamage.toString()
        ATTACK_DAMAGE_LVL -> stats.attackDamagePerLevel.numberToString()
        ATTACK_SPEED -> stats.attackSpeed.numberToString()
        ATTACK_SPEED_LVL -> stats.attackSpeedPerLevel.numberToString()
    }
}

internal fun Champion.getStatByOrderAsDouble(order: ChampionOrder): Double {
    return when (order) {
        ALPHABETIC -> 0.0 // Don't want to show stats for this order.
        HP -> stats.hp.toDouble()
        HP_LVL -> stats.hpPerLevel.toDouble()
        MP -> stats.mp
        MP_LVL -> stats.mpPerLevel
        MOVE_SPEED -> stats.moveSpeed.toDouble()
        ARMOR -> stats.armor.toDouble()
        ARMOR_LVL -> stats.armorPerLevel
        MAGIC_RESIST -> stats.spellBlock.toDouble()
        MAGIC_RESIST_LVL -> stats.spellBlockPerLevel
        ATTACK_RANGE -> stats.attackRange.toDouble()
        HP_REGEN -> stats.hpRegen
        HP_REGEN_LVL -> stats.hpRegenPerLevel
        MP_REGEN -> stats.mpRegen
        MP_REGEN_LVL -> stats.mpRegenPerLevel
        CRIT -> stats.crit.toDouble()
        CRIT_LVL -> stats.critPerLevel.toDouble()
        ATTACK_DAMAGE -> stats.attackDamage.toDouble()
        ATTACK_DAMAGE_LVL -> stats.attackDamagePerLevel
        ATTACK_SPEED -> stats.attackSpeed
        ATTACK_SPEED_LVL -> stats.attackSpeedPerLevel
    }
}


/**
 * Return champion position on list as String.
 * Example: 0 = "1"
 */
fun Champion.positionFromIndexAsText(index: Int?): String? {
    return if (index == null) null
    else (index + 1).toString()
}

/**
 * Format Double to String as number
 */
fun Double.numberToString(): String {
    val asString = toString()
    return if (asString.endsWith(".0")) toInt().toString()
    else asString
}
