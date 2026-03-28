package io.izzel.arclight.mixin;

import io.izzel.arclight.mixin.annotation.CreateConstructorProcessor;
import io.izzel.arclight.mixin.annotation.MixinProcessor;
import io.izzel.arclight.mixin.annotation.RenameIntoProcessor;
import io.izzel.arclight.mixin.annotation.TransformAccessProcessor;
import io.izzel.arclight.mixin.injector.Decorator;
import io.izzel.arclight.mixin.injector.DecoratorInfo;
import io.izzel.arclight.mixin.injector.EjectorInfo;
import java.util.List;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public class MixinTools {

    private final static List<MixinProcessor> postProcessors = List.of(
            new RenameIntoProcessor(),
            new TransformAccessProcessor(),
            new CreateConstructorProcessor()
    );

    public static void setup() {
        InjectionInfo.register(EjectorInfo.class);
        InjectionInfo.register(DecoratorInfo.class);
    }

    public static void onPostMixin(String targetClassName, ClassNode targetClass, IMixinInfo mixinInfo) {
        for (var processor : postProcessors) {
            processor.accept(targetClassName, targetClass, mixinInfo);
        }
        Decorator.postMixin(targetClass);
    }
}
