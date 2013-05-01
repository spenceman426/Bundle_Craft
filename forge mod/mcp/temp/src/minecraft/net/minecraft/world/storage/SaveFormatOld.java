package net.minecraft.world.storage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class SaveFormatOld implements ISaveFormat {

   protected final File field_75808_a;


   public SaveFormatOld(File p_i3913_1_) {
      if(!p_i3913_1_.exists()) {
         p_i3913_1_.mkdirs();
      }

      this.field_75808_a = p_i3913_1_;
   }

   @SideOnly(Side.CLIENT)
   public List func_75799_b() throws AnvilConverterException {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < 5; ++var2) {
         String var3 = "World" + (var2 + 1);
         WorldInfo var4 = this.func_75803_c(var3);
         if(var4 != null) {
            var1.add(new SaveFormatComparator(var3, "", var4.func_76057_l(), var4.func_76092_g(), var4.func_76077_q(), false, var4.func_76093_s(), var4.func_76086_u()));
         }
      }

      return var1;
   }

   public void func_75800_d() {}

   public WorldInfo func_75803_c(String p_75803_1_) {
      File var2 = new File(this.field_75808_a, p_75803_1_);
      if(!var2.exists()) {
         return null;
      } else {
         File var3 = new File(var2, "level.dat");
         NBTTagCompound var4;
         NBTTagCompound var5;
         if(var3.exists()) {
            try {
               var4 = CompressedStreamTools.func_74796_a(new FileInputStream(var3));
               var5 = var4.func_74775_l("Data");
               return new WorldInfo(var5);
            } catch (Exception var7) {
               var7.printStackTrace();
            }
         }

         var3 = new File(var2, "level.dat_old");
         if(var3.exists()) {
            try {
               var4 = CompressedStreamTools.func_74796_a(new FileInputStream(var3));
               var5 = var4.func_74775_l("Data");
               return new WorldInfo(var5);
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }

         return null;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_75806_a(String p_75806_1_, String p_75806_2_) {
      File var3 = new File(this.field_75808_a, p_75806_1_);
      if(var3.exists()) {
         File var4 = new File(var3, "level.dat");
         if(var4.exists()) {
            try {
               NBTTagCompound var5 = CompressedStreamTools.func_74796_a(new FileInputStream(var4));
               NBTTagCompound var6 = var5.func_74775_l("Data");
               var6.func_74778_a("LevelName", p_75806_2_);
               CompressedStreamTools.func_74799_a(var5, new FileOutputStream(var4));
            } catch (Exception var7) {
               var7.printStackTrace();
            }
         }

      }
   }

   public boolean func_75802_e(String p_75802_1_) {
      File var2 = new File(this.field_75808_a, p_75802_1_);
      if(!var2.exists()) {
         return true;
      } else {
         System.out.println("Deleting level " + p_75802_1_);

         for(int var3 = 1; var3 <= 5; ++var3) {
            System.out.println("Attempt " + var3 + "...");
            if(func_75807_a(var2.listFiles())) {
               break;
            }

            System.out.println("Unsuccessful in deleting contents.");
            if(var3 < 5) {
               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var5) {
                  ;
               }
            }
         }

         return var2.delete();
      }
   }

   protected static boolean func_75807_a(File[] p_75807_0_) {
      for(int var1 = 0; var1 < p_75807_0_.length; ++var1) {
         File var2 = p_75807_0_[var1];
         System.out.println("Deleting " + var2);
         if(var2.isDirectory() && !func_75807_a(var2.listFiles())) {
            System.out.println("Couldn\'t delete directory " + var2);
            return false;
         }

         if(!var2.delete()) {
            System.out.println("Couldn\'t delete file " + var2);
            return false;
         }
      }

      return true;
   }

   public ISaveHandler func_75804_a(String p_75804_1_, boolean p_75804_2_) {
      return new SaveHandler(this.field_75808_a, p_75804_1_, p_75804_2_);
   }

   public boolean func_75801_b(String p_75801_1_) {
      return false;
   }

   public boolean func_75805_a(String p_75805_1_, IProgressUpdate p_75805_2_) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_90033_f(String p_90033_1_) {
      File var2 = new File(this.field_75808_a, p_90033_1_);
      return var2.isDirectory();
   }
}
