package net.sybar.garbagecan

import cn.nukkit.event.EventHandler
import cn.nukkit.plugin.PluginBase
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerInteractEvent

class main: PluginBase(), Listener {

    var double_touch = mutableMapOf<String, Long>()

    override fun onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public fun onTouch(ev: PlayerInteractEvent){
        var player = ev.getPlayer()
        var name = player.getName()
        var block = ev.getBlock()
        if (block.getId().equals(120)){
            var item = player.getInventory().getItemInHand();
            if(item.getId().equals(0)){return}
            if(this.double_touch.containsKey(name)){
                var now = System.currentTimeMillis() / 1000
                var time = this.double_touch.get(name)
                var diff = (now - time!!)
                System.out.println(diff)
                if(diff < 1){
                    player.getInventory().remove(item)
                    player.sendMessage("§aゴミ箱に捨てました")
                    this.double_touch.remove(name)
                }else{
                    player.sendMessage("§c捨てるにはもう一度タップ")
                    this.double_touch.replace(name, now)
                }
            }else {
                player.sendMessage("§c捨てるにはもう一度タップ")
                this.double_touch.set(name, System.currentTimeMillis() / 1000)
            }
        }
    }
}