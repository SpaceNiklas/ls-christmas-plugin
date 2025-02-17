package com.learnspigot.event.listener

import com.learnspigot.event.ChristmasEvent.Companion.LOBBY_SPAWN
import com.learnspigot.event.engine.GameEngine
import com.learnspigot.event.util.npc.NPC
import com.learnspigot.event.visual.TablistManager
import gg.flyte.twilight.event.event
import gg.flyte.twilight.scheduler.delay
import org.bukkit.GameMode
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object ConnectionListener {

    init {
        event<PlayerJoinEvent>(EventPriority.HIGHEST, ignoreCancelled = true) {
            player.sendMessage("Welcome to the server!")
        }



// test commitadwadw



        event<PlayerJoinEvent> {
            joinMessage(null)

            player.apply {
                GameEngine.onPlayerJoin(this)

                gameMode = GameMode.ADVENTURE
                foodLevel = 20
                health = 20.0
                addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, Int.MAX_VALUE, 10, false, false))
                teleport(LOBBY_SPAWN)

                inventory.clear()
                //inventory.helmet = ItemStack(Material.CARVED_PUMPKIN)

                delay(20) {
                    NPC.sendAll(this@apply)
                    TablistManager.set(this@apply)
                }
            }
        }

        event<PlayerResourcePackStatusEvent> {

        }

        event<PlayerQuitEvent> {
            quitMessage(null)

            player.apply {
                GameEngine.onPlayerQuit(this)

                TablistManager.remove(this)
            }
        }
    }

}