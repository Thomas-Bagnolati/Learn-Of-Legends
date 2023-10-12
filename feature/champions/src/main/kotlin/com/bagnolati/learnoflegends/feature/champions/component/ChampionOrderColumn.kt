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
                selectedOrder = ChampionOrder.ALPHABETIC,
                expandFab = true,
                onClickChampionOrder = {},
                onClickFloatingActionButton = {}
            )
        }
    }
}


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
 * Format Double to String as number
 */
fun Double.asTextNumber(): String {
    val asString = toString()
    return if (asString.endsWith(".0")) toInt().toString()
    else asString
}

/**
 * Get corresponding stat by [ChampionOrder]
 *
 * [ChampionOrder.ALPHABETIC] always return 0.0
 */
fun Champion.getStatByOrder(order: ChampionOrder): Double {
    return when (order) {
        ChampionOrder.ALPHABETIC -> stats.alphabetic
        ChampionOrder.HP -> stats.hp
        ChampionOrder.HP_LVL -> stats.hpPerLevel
        ChampionOrder.MOVE_SPEED -> stats.moveSpeed
        ChampionOrder.ARMOR -> stats.armor
        ChampionOrder.MAGIC_RESIST -> stats.spellBlock
        ChampionOrder.ATTACK_RANGE -> stats.attackRange
        ChampionOrder.CRIT -> stats.crit
        ChampionOrder.CRIT_LVL -> stats.critPerLevel
        ChampionOrder.ATTACK_DAMAGE -> stats.attackDamage
        ChampionOrder.MP -> stats.mp
        ChampionOrder.MP_LVL -> stats.mpPerLevel
        ChampionOrder.ARMOR_LVL -> stats.armorPerLevel
        ChampionOrder.MAGIC_RESIST_LVL -> stats.spellBlockPerLevel
        ChampionOrder.HP_REGEN -> stats.hpRegen
        ChampionOrder.HP_REGEN_LVL -> stats.hpRegenPerLevel
        ChampionOrder.MP_REGEN -> stats.mpRegen
        ChampionOrder.MP_REGEN_LVL -> stats.mpRegenPerLevel
        ChampionOrder.ATTACK_DAMAGE_LVL -> stats.attackDamagePerLevel
        ChampionOrder.ATTACK_SPEED -> stats.attackSpeed
        ChampionOrder.ATTACK_SPEED_LVL -> stats.attackSpeedPerLevel
    }
}
