/*
 * Copyright 2021 m.bignami.
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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.dice.bag.D6;

/**
 *
 * @author m.bignami
 */
public abstract class YearZeroBase
{
    protected final Set<YearZeroModifiers> mods;

    public YearZeroBase(Collection<YearZeroModifiers> mod)
    {
        this.mods = new HashSet<>();
        this.mods.addAll(mod);
    }
    
    protected YearZeroResults buildResults(List<SingleResult<Integer>> baseResult, List<SingleResult<Integer>> gearResult, List<SingleResult<Integer>> skillResult, List<SingleResult<Integer>> stressResult)
    {
        YearZeroResults results = new YearZeroResults(baseResult, gearResult, skillResult, stressResult);
        results.setVerbose(mods.contains(YearZeroModifiers.VERBOSE));
        for (SingleResult<Integer> r : baseResult)
        {
            if (r.getValue() >= 6)
            {
                results.addSuccess();
            }
            if (r.getValue() == 1)
            {
                results.addBaseBane();
            }
        }
        for (SingleResult<Integer> r : gearResult)
        {
            if (r.getValue() >= 6)
            {
                results.addSuccess();
            }
            if (r.getValue() >= 8)
            {
                results.addSuccess();
            }
            if (r.getValue() >= 10)
            {
                results.addSuccess();
            }
            if (r.getValue() == 12)
            {
                results.addSuccess();
            }
            if (r.getValue() == 1)
            {
                results.addGearBane();
            }
        }
        for (SingleResult<Integer> r : skillResult)
        {
            if (r.getValue() >= 6)
            {
                results.addSuccess();
            }
        }
        for (SingleResult<Integer> r : stressResult)
        {
            if (r.getValue() >= 6)
            {
                results.addSuccess();
            }
            if (r.getValue() == 1)
            {
                results.addStressBane();
                results.setPanic(true);
            }
        }
        if (results.isPanic())
        {
            SingleResult<Integer> panicRoll, tmp = D6.INSTANCE.nextResult().get();
            panicRoll = new SingleResult<>(tmp.getLabel() + "+" + stressResult.size(), tmp.getValue() + stressResult.size());
            results.setPanicResult(panicRoll);
        }
        return results;
    }
}
