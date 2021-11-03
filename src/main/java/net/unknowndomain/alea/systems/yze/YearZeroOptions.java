/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.yze;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import net.unknowndomain.alea.systems.annotations.RpgSystemData;
import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.yze.RpgSystemBundle")
public class YearZeroOptions extends RpgSystemOptions
{
    @RpgSystemOption(name = "base", shortcode = "b", description = "yze.options.baseDice", argName = "baseDice")
    private Integer base;
    @RpgSystemOption(name = "gear", shortcode = "g", description = "yze.options.gearDice", argName = "gearDice")
    private Integer gear;
    @RpgSystemOption(name = "skill", shortcode = "s", description = "yze.options.skillDice", argName = "skillDice")
    private Integer skill;
    @RpgSystemOption(name = "modifier", shortcode = "m", description = "yze.options.modifier", argName = "modifier")
    private Integer poolModifier;
    @RpgSystemOption(name = "stress", shortcode = "t", description = "yze.options.stressDice", argName = "stressDice")
    private Integer stress;
    @RpgSystemOption(name = "push", shortcode = "p", description = "yze.options.push")
    private boolean push;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp() || !(isBasic() ^ isPush()));
    }
    
    public boolean isBasic()
    {
        return base != null;
    }
    
    public boolean isPush()
    {
        return push;
    }

    public Collection<YearZeroModifiers> getModifiers()
    {
        Set<YearZeroModifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(YearZeroModifiers.VERBOSE);
        }
        if (isPush())
        {
            mods.add(YearZeroModifiers.PUSH);
        }
        return mods;
    }

    public Integer getBase()
    {
        return base;
    }

    public void setBase(Integer base)
    {
        this.base = base;
    }

    public Integer getGear()
    {
        return gear;
    }

    public void setGear(Integer gear)
    {
        this.gear = gear;
    }

    public Integer getSkill()
    {
        return skill;
    }

    public void setSkill(Integer skill)
    {
        this.skill = skill;
    }

    public Integer getPoolModifier()
    {
        return poolModifier;
    }

    public void setPoolModifier(Integer poolModifier)
    {
        this.poolModifier = poolModifier;
    }

    public Integer getStress()
    {
        return stress;
    }

    public void setStress(Integer stress)
    {
        this.stress = stress;
    }
    
}