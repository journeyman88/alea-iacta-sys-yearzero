/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.yze;

import java.util.Arrays;
import java.util.Collection;
import net.unknowndomain.alea.random.dice.DicePool;
import net.unknowndomain.alea.random.dice.bag.D6;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class YearZeroRoll extends YearZeroBase implements GenericRoll
{
    
    private final DicePool<D6> basePool;
    private final DicePool<D6> gearPool;
    private final DicePool<D6> skillPool;
    private final DicePool<D6> stressPool;
    
    public YearZeroRoll(Integer baseDice, Integer gearDice, Integer skillDice, Integer stressDice, Integer modifier, YearZeroModifiers ... mod)
    {
        this(baseDice, gearDice, skillDice, stressDice, modifier, Arrays.asList(mod));
    }
    
    public YearZeroRoll(Integer baseDice, Integer gearDice, Integer skillDice, Integer stressDice, Integer modifier, Collection<YearZeroModifiers> mod)
    {
        super(mod);
        int base = 0;
        if (baseDice != null)
        {
            base = baseDice;
        }
        int gear = 0;
        if (gearDice != null)
        {
            gear = gearDice;
        }
        int skill = 0;
        if (skillDice != null)
        {
            skill = skillDice;
        }
        if (modifier != null)
        {
            skill += modifier;
        }
        if (skill < 0)
        {
            gear += skill;
            skill = 0;
        }
        if (gear < 0)
        {
            base += gear;
            gear = 0;
        }
        int stress = 0;
        if (stressDice != null)
        {
            stress = stressDice;
        }
        this.basePool = new DicePool<>(D6.INSTANCE, base);
        this.gearPool = new DicePool<>(D6.INSTANCE, gear);
        this.skillPool = new DicePool<>(D6.INSTANCE, skill);
        this.stressPool = new DicePool<>(D6.INSTANCE, stress);
    }
    
    @Override
    public GenericResult getResult()
    {
        return buildResults(this.basePool.getResults(), this.gearPool.getResults(), this.skillPool.getResults(), this.stressPool.getResults());
    }
    
    
}
