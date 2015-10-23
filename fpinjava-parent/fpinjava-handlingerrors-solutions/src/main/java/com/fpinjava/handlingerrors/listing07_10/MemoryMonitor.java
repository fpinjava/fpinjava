package com.fpinjava.handlingerrors.listing07_10;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;


public class MemoryMonitor {


    public static void main(String[] args) {
      monitorMemory(0.8);
    }


  public static void monitorMemory(double threshold) {
    findPSOldGenPool().forEachOrThrow(poolMxBean -> poolMxBean.setCollectionUsageThreshold((int) Math.floor(poolMxBean
        .getUsage().getMax() * threshold)));

    NotificationEmitter emitter = (NotificationEmitter) ManagementFactory.getMemoryMXBean();
    emitter.addNotificationListener(notificationListener, null, null);
  }

  private static NotificationListener notificationListener = (Notification notification, Object handBack) -> {
    if (notification.getType().equals(MemoryNotificationInfo.MEMORY_COLLECTION_THRESHOLD_EXCEEDED)) {
      // cleanly shutdown the application;
    }
  };

  private static Result<MemoryPoolMXBean> findPSOldGenPool() {
    return List.fromCollection(ManagementFactory.getMemoryPoolMXBeans())
               .first(x -> x.getName().equals("PS Old Gen"))
        .mapFailure("Could not find PS Old Gen memory pool");
  }
}
