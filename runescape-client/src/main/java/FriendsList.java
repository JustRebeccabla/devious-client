import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("pr")
@Implements("FriendsList")
public class FriendsList extends UserList {
   @ObfuscatedName("bt")
   @ObfuscatedSignature(
      descriptor = "Lqs;"
   )
   static Bounds field4572;
   @ObfuscatedName("av")
   @ObfuscatedSignature(
      descriptor = "Lsj;"
   )
   @Export("loginType")
   final LoginType loginType;
   @ObfuscatedName("as")
   @ObfuscatedGetter(
      intValue = -534786145
   )
   int field4575 = 1;
   @ObfuscatedName("ax")
   @ObfuscatedSignature(
      descriptor = "Lor;"
   )
   @Export("friendLoginUpdates")
   public LinkDeque friendLoginUpdates = new LinkDeque();

   @ObfuscatedSignature(
      descriptor = "(Lsj;)V"
   )
   public FriendsList(LoginType var1) {
      super(400);
      this.loginType = var1;
   }

   @ObfuscatedName("at")
   @ObfuscatedSignature(
      descriptor = "(I)Lqm;",
      garbageValue = "328216456"
   )
   @Export("newInstance")
   User newInstance() {
      return new Friend();
   }

   @ObfuscatedName("an")
   @ObfuscatedSignature(
      descriptor = "(IB)[Lqm;",
      garbageValue = "20"
   )
   @Export("newTypedArray")
   User[] newTypedArray(int var1) {
      return new Friend[var1];
   }

   @ObfuscatedName("av")
   @ObfuscatedSignature(
      descriptor = "(Lun;ZB)Z",
      garbageValue = "11"
   )
   @Export("isFriended")
   public boolean isFriended(Username var1, boolean var2) {
      Friend var3 = (Friend)this.getByUsername(var1);
      if (var3 == null) {
         return false;
      } else {
         return !var2 || var3.world != 0;
      }
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      descriptor = "(Ltz;IB)V",
      garbageValue = "30"
   )
   @Export("read")
   public void read(Buffer var1, int var2) {
      while(true) {
         if (var1.offset < var2) {
            boolean var3 = var1.readUnsignedByte() == 1;
            Username var4 = new Username(var1.readStringCp1252NullTerminated(), this.loginType);
            Username var5 = new Username(var1.readStringCp1252NullTerminated(), this.loginType);
            int var6 = var1.readUnsignedShort();
            int var7 = var1.readUnsignedByte();
            int var8 = var1.readUnsignedByte();
            boolean var9 = (var8 & 2) != 0;
            boolean var10 = (var8 & 1) != 0;
            if (var6 > 0) {
               var1.readStringCp1252NullTerminated();
               var1.readUnsignedByte();
               var1.readInt();
            }

            var1.readStringCp1252NullTerminated();
            if (var4 != null && var4.hasCleanName()) {
               Friend var11 = (Friend)this.getByCurrentUsername(var4);
               if (var3) {
                  Friend var12 = (Friend)this.getByCurrentUsername(var5);
                  if (var12 != null && var11 != var12) {
                     if (var11 != null) {
                        this.remove(var12);
                     } else {
                        var11 = var12;
                     }
                  }
               }

               if (var11 != null) {
                  this.changeName(var11, var4, var5);
                  if (var6 != var11.world) {
                     boolean var14 = true;

                     for(FriendLoginUpdate var13 = (FriendLoginUpdate)this.friendLoginUpdates.last(); var13 != null; var13 = (FriendLoginUpdate)this.friendLoginUpdates.previous()) {
                        if (var13.username.equals(var4)) {
                           if (var6 != 0 && var13.world == 0) {
                              var13.remove();
                              var14 = false;
                           } else if (var6 == 0 && var13.world != 0) {
                              var13.remove();
                              var14 = false;
                           }
                        }
                     }

                     if (var14) {
                        this.friendLoginUpdates.addFirst(new FriendLoginUpdate(var4, var6));
                     }
                  }
               } else {
                  if (this.getSize() >= 400) {
                     continue;
                  }

                  var11 = (Friend)this.addLast(var4, var5);
               }

               if (var6 != var11.world) {
                  var11.int2 = ++this.field4575 - 1;
                  if (var11.world == -1 && var6 == 0) {
                     var11.int2 = -(var11.int2 * -1981175651) * 819600821;
                  }

                  var11.world = var6;
               }

               var11.rank = var7;
               var11.field4589 = var9;
               var11.field4590 = var10;
               continue;
            }

            throw new IllegalStateException();
         }

         this.sort();
         return;
      }
   }

   @ObfuscatedName("kw")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1087298650"
   )
   @Export("Widget_runOnTargetLeave")
   static void Widget_runOnTargetLeave() {
      if (Client.isSpellSelected) {
         Widget var0 = class158.getWidgetChild(ScriptFrame.selectedSpellWidget, Client.selectedSpellChildIndex);
         if (var0 != null && var0.onTargetLeave != null) {
            ScriptEvent var1 = new ScriptEvent();
            var1.widget = var0;
            var1.args = var0.onTargetLeave;
            Clock.runScriptEvent(var1);
         }

         Client.selectedSpellItemId = -1;
         Client.isSpellSelected = false;
         Messages.invalidateWidget(var0);
      }
   }
}
