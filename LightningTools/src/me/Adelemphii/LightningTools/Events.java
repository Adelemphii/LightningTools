package me.Adelemphii.LightningTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {
	
	int ATTACK_REACH = 50;
	
	public List<String> list = new ArrayList<String>();
	
	static Main main;
	public Events(Main instance) {
		main = instance;
	}
	
	@EventHandler()
	public void onClick(PlayerInteractEvent event) {
		
		if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_SWORD))
			if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player) event.getPlayer();
				// Right Click
				if(event.getAction() == Action.RIGHT_CLICK_AIR) {
					summonLightning(player);
				}
				
				// Left Click
				if(event.getAction() == Action.LEFT_CLICK_AIR) {
					if(!list.contains(player.getName()))
						list.add(player.getName());
				}
			}
		if(list.contains(event.getPlayer().getName())) {
			list.remove(event.getPlayer().getName());
		}
		
	}
	
	@EventHandler()
	public void onHit(EntityDamageByEntityEvent event) {
		// I'm an idiot and forgot to check if they're holding the weapon.
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SWORD) {
				if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
					World world = player.getWorld();
					// summon lightning
					Location loc = event.getEntity().getLocation();
					loc.setY(loc.getY() + 1);
				
					for (int i = 1; i < 4; i++) {
						world.strikeLightning(event.getEntity().getLocation());
					}
				}
			}
		}
	}
	
	public void summonLightning(Player player) {
		if(player.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SWORD) {
			if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				
				Location observerPos = player.getEyeLocation();
				Vector3D observerDir = new Vector3D(observerPos.getDirection());
				
				Vector3D observerStart = new Vector3D(observerPos);
				Vector3D observerEnd = observerStart.add(observerDir.multiply(ATTACK_REACH));
				
				Entity hit = null;
				
                // Get nearby entities
                for (Entity target : player.getWorld().getEntities()) {
                    // Bounding box of the given player
                    Vector3D targetPos = new Vector3D(target.getLocation());
                    Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
                    Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
 
                    if (target != player && hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                        if (hit == null || 
                                hit.getLocation().distanceSquared(observerPos) > 
                                target.getLocation().distanceSquared(observerPos)) {
 
                            hit = target;
                        }
                    }
                }
 
                // Hit the closest player if they're within LOS
                if (hit != null) {
                	if(player.hasLineOfSight(hit)) {
                	World world = hit.getWorld();
                	world.strikeLightning(hit.getLocation());
                	}
                }
            }
        }
    }
	
    // Source:
   // [url]http://www.gamedev.net/topic/338987-aabb---line-segment-intersection-test/[/url]
   private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
       final double epsilon = 0.0001f;

       Vector3D d = p2.subtract(p1).multiply(0.5);
       Vector3D e = max.subtract(min).multiply(0.5);
       Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
       Vector3D ad = d.abs();

       if (Math.abs(c.x) > e.x + ad.x)
           return false;
       if (Math.abs(c.y) > e.y + ad.y)
           return false;
       if (Math.abs(c.z) > e.z + ad.z)
           return false;

       if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
           return false;
       if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
           return false;
       if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
           return false;

       return true;
   }
}










