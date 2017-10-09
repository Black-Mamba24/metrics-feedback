package com.zhaiyi.metrics_feedback.constants;

/**
 * Created by zhaiyi on 2017/9/29.
 */
public final class JvmConstants {
    /* prefix */
    public static final String jvmGC = "jvm.gc";
    public static final String jvmMemory = "jvm.memory";
    public static final String jvmThreadStates = "jvm.thread-states";

    /* memory */
    public static final String heapCommitted = "heap.committed";
    public static final String heapInit = "heap.init";
    public static final String heapMax = "heap.max";
    public static final String heapUsage = "heap.usage";
    public static final String heapUsed = "heap.used";
    public static final String nonHeapcommitted = "non-heap.committed";
    public static final String nonHeapInit = "non-heap.init";
    public static final String nonHeapMax = "non-heap.max";
    public static final String nonHeapUsage = "non-heap.usage";
    public static final String nonHeapUsed = "non-heap.used";
    public static final String poolsCodeCachecommitted = "pools.Code-Cache.committed";
    public static final String poolsCodeCacheInit = "pools.Code-Cache.init";
    public static final String poolsCodeCacheMax = "pools.Code-Cache.max";
    public static final String poolsCodeCacheUsage = "pools.Code-Cache.usage";
    public static final String poolsCodeCacheUsed = "pools.Code-Cache.used";
    public static final String poolsCompressedClassSpacecommitted= "pools.Compressed-Class-Space.committed";
    public static final String poolsCompressedClassSpaceInit = "pools.Compressed-Class-Space.init";
    public static final String poolsCompressedClassSpaceMax = "pools.Compressed-Class-Space.max";
    public static final String poolsCompressedClassSpaceUsage = "pools.Compressed-Class-Space.usage";
    public static final String poolsCompressedClassSpaceUsed = "pools.Compressed-Class-Space.used";
    public static final String poolsMetaspaceCommitted= "pools.Metaspace.committed";
    public static final String poolsMetaspaceInit = "pools.Metaspace.init";
    public static final String poolsMetaspaceMax = "pools.Metaspace.max";
    public static final String poolsMetaspaceUsage = "pools.Metaspace.usage";
    public static final String poolsMetaspaceUsed = "pools.Metaspace.used";
    public static final String poolsPSEdenSpaceCommitted= "pools.PS-Eden-Space.committed";
    public static final String poolsPSEdenSpaceInit = "pools.PS-Eden-Space.init";
    public static final String poolsPSEdenSpaceMax = "pools.PS-Eden-Space.max";
    public static final String poolsPSEdenSpaceUsage = "pools.PS-Eden-Space.usage";
    public static final String poolsPSEdenSpaceUsed = "pools.PS-Eden-Space.used";
    public static final String poolsPSOldGenCommitted= "pools.PS-Old-Gen.committed";
    public static final String poolsPSOldGenInit = "pools.PS-Old-Gen.init";
    public static final String poolsPSOldGenMax = "pools.PS-Old-Gen.max";
    public static final String poolsPSOldGenUsage = "pools.PS-Old-Gen.usage";
    public static final String poolsPSOldGenUsed = "pools.PS-Old-Gen.used";
    public static final String poolsPSSurvivorSpacecommitted= "pools.PS-Survivor-Space.committed";
    public static final String poolsPSSurvivorSpaceInit = "pools.PS-Survivor-Space.init";
    public static final String poolsPSSurvivorSpaceMax = "pools.PS-Survivor-Space.max";
    public static final String poolsPSSurvivorSpaceUsage = "pools.PS-Survivor-Space.usage";
    public static final String poolsPSSurvivorSpaceUsed = "pools.PS-Survivor-Space.used";
    public static final String totalcommitted = "total.committed";
    public static final String totalInit = "total.init";
    public static final String totalMax = "total.max";
    public static final String totalUsed = "total.used";

    /* gc 待补充 */
    public static final String PSMarkSweepCount = "PS-MarkSweep.count";
    public static final String PSMarkSweepTime = "PS-MarkSweep.time";
    public static final String PSScavengeCount = "PS-Scavenge.count";
    public static final String PSScavengeTime = "PS-Scavenge.time";

    /* thread states */
    public static final String blockedCount = "blocked.count";
    public static final String threadCount = "count";
    public static final String daemonCount = "daemon.count";
    public static final String deadlockCount = "deadlock.count";
    public static final String newCount = "new.count";
    public static final String runnableCount = "runnable.count";
    public static final String terminatedCount = "terminated.count";
    public static final String timedWaitingCount = "timed_waiting.count";
    public static final String waitingCount = "waiting.count";
}
