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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.dice.DicePool;
import net.unknowndomain.alea.random.dice.bag.D6;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.StatefulRoll;

/**
 *
 * @author journeyman
 */
public class YearZeroReroll extends YearZeroBase implements StatefulRoll
{
    
    private YearZeroResults prev;
    
    public YearZeroReroll(YearZeroModifiers ... mod)
    {
        this(Arrays.asList(mod));
    }
    
    public YearZeroReroll(Collection<YearZeroModifiers> mod)
    {
        super(mod);
    }
    
    @Override
    public GenericResult getResult()
    {
        YearZeroResults results = prev;
        YearZeroResults results2 = null;
        if (mods.contains(YearZeroModifiers.PUSH) && (results.getPrev() == null) )
        {
            List<SingleResult<Integer>> base = new ArrayList<>();
            int baseCount = 0;
            for (SingleResult<Integer> pr : results.getBaseResults())
            {
                if ((pr.getValue() == 1) || (pr.getValue() >= 6))
                {
                    base.add(pr);
                }
                else
                {
                    baseCount++;
                }
            }
            base.addAll(new DicePool<>(D6.INSTANCE, baseCount).getResults());
            List<SingleResult<Integer>> gear = new ArrayList<>();
            int gearCount = 0;
            for (SingleResult<Integer> pr : results.getGearResults())
            {
                if ((pr.getValue() == 1) || (pr.getValue() >= 6))
                {
                    gear.add(pr);
                }
                else
                {
                    gearCount++;
                }
            }
            gear.addAll(new DicePool<>(D6.INSTANCE, gearCount).getResults());
            List<SingleResult<Integer>> skill = new ArrayList<>();
            int skillCount = 0;
            for (SingleResult<Integer> pr : results.getSkillResults())
            {
                if (pr.getValue() >= 6)
                {
                    skill.add(pr);
                }
                else
                {
                    skillCount++;
                }
            }
            skill.addAll(new DicePool<>(D6.INSTANCE, skillCount).getResults());
            List<SingleResult<Integer>> stress = new ArrayList<>();
            int stressCount = 0;
            for (SingleResult<Integer> pr : results.getStressResults())
            {
                if ((pr.getValue() == 1) || (pr.getValue() >= 6))
                {
                    stress.add(pr);
                }
                else
                {
                    stressCount++;
                }
            }
            stress.addAll(new DicePool<>(D6.INSTANCE, stressCount).getResults());
            results2 = results;
            results = buildResults(base, gear, skill, stress);
            results.setPrev(results2);
        }
        return results;
    }
    
    @Override
    public boolean loadState(GenericResult state)
    {
        boolean retVal = false;
        if (state instanceof YearZeroResults)
        {
            prev = (YearZeroResults) state;
            retVal = true;
        }
        return retVal;
    }
    
}
